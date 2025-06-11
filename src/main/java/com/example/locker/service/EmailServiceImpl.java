package com.example.locker.service;

import com.example.locker.dto.AluguelDTO;
import com.example.locker.dto.ClienteDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailRemetente;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private String lerConteudoHtml(String nomeArquivo) {
        try {
            ClassPathResource resource = new ClassPathResource("templates/emails/" + nomeArquivo);
            return FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            logger.error("Erro ao ler template de email: {}", nomeArquivo, e);
            throw new UncheckedIOException("Não foi possível ler o template de email: " + nomeArquivo, e);
        }
    }

    @Override
    @Async
    public void enviarEmailBoasVindasCliente(ClienteDTO cliente) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(emailRemetente);
            helper.setTo(cliente.getEmail());
            helper.setSubject("Bem-vindo(a) ao Nosso Serviço de Guarda-Volumes!");

            String htmlConteudo = lerConteudoHtml("email_bem_vindo.html");

            htmlConteudo = htmlConteudo.replace("**{NOME_CLIENTE}**", cliente.getNome());
            htmlConteudo = htmlConteudo.replace("**{ANO_ATUAL}**", String.valueOf(LocalDateTime.now().getYear()));

            helper.setText(htmlConteudo, true);

            javaMailSender.send(mimeMessage);
            logger.info("Email HTML de boas-vindas enviado para: {}", cliente.getEmail());
        } catch (MessagingException | MailException e) {
            logger.error("Erro ao enviar email HTML de boas-vindas para {}: {}", cliente.getEmail(), e.getMessage(), e);
        } catch (UncheckedIOException e) {
            logger.error("Falha ao carregar template de boas-vindas para {}: {}", cliente.getEmail(), e.getMessage(), e);
        }
    }

    @Override
    @Async
    public void enviarEmailAtualizacaoCliente(ClienteDTO cliente) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(emailRemetente);
            helper.setTo(cliente.getEmail());
            helper.setSubject("Seus Dados Cadastrais Foram Atualizados");

            String htmlConteudo = lerConteudoHtml("email_atualizacao_cliente.html");

            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                    .withLocale(new Locale("pt", "BR"));
            String dataAtualizacao = LocalDateTime.now().format(formatter);

            htmlConteudo = htmlConteudo.replace("**{NOME_CLIENTE}**", cliente.getNome());
            htmlConteudo = htmlConteudo.replace("**{DATA_ATUALIZACAO}**", dataAtualizacao);
            htmlConteudo = htmlConteudo.replace("**{NOME_CLIENTE_NOVO}**", cliente.getNome());
            htmlConteudo = htmlConteudo.replace("**{EMAIL_CLIENTE_NOVO}**", cliente.getEmail());
            htmlConteudo = htmlConteudo.replace("**{TELEFONE_CLIENTE_NOVO}**", cliente.getTelefone() != null ? cliente.getTelefone() : "Não informado");
            htmlConteudo = htmlConteudo.replace("**{ANO_ATUAL}**", String.valueOf(LocalDateTime.now().getYear()));

            helper.setText(htmlConteudo, true);

            javaMailSender.send(mimeMessage);
            logger.info("Email HTML de atualização de dados enviado para: {}", cliente.getEmail());
        } catch (MessagingException | MailException e) {
            logger.error("Erro ao enviar email HTML de atualização de dados para {}: {}", cliente.getEmail(), e.getMessage(), e);
        } catch (UncheckedIOException e) {
            logger.error("Falha ao carregar template de atualização de cliente para {}: {}", cliente.getEmail(), e.getMessage(), e);
        }
    }

    @Override
    @Async
    public void enviarEmailExclusaoCliente(ClienteDTO clienteExcluido) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(emailRemetente);
            helper.setTo(clienteExcluido.getEmail());
            helper.setSubject("Confirmação de Exclusão de Conta - Guarda-Volumes do Zé");

            String htmlConteudo = lerConteudoHtml("email_exclusao_cliente.html");

            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                    .withLocale(new Locale("pt", "BR"));
            String dataExclusao = LocalDateTime.now().format(formatter);

            htmlConteudo = htmlConteudo.replace("**{NOME_CLIENTE}**", clienteExcluido.getNome());
            htmlConteudo = htmlConteudo.replace("**{DATA_EXCLUSAO}**", dataExclusao);
            htmlConteudo = htmlConteudo.replace("**{ANO_ATUAL}**", String.valueOf(LocalDateTime.now().getYear()));

            helper.setText(htmlConteudo, true);

            javaMailSender.send(mimeMessage);
            logger.info("Email HTML de exclusão de conta enviado para: {}", clienteExcluido.getEmail());
        } catch (MessagingException | MailException e) {
            logger.error("Erro ao enviar email HTML de exclusão de conta para {}: {}", clienteExcluido.getEmail(), e.getMessage(), e);
        } catch (UncheckedIOException e) {
            logger.error("Falha ao carregar template de exclusão de cliente para {}: {}", clienteExcluido.getEmail(), e.getMessage(), e);
        }
    }
    @Async
    @Override
    public void enviarEmailConfirmacaoAluguel(AluguelDTO aluguel) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(emailRemetente);
            helper.setTo(aluguel.getCliente().getEmail());
            helper.setSubject("Seu Aluguel foi Confirmado! - Guarda-Volumes do Zé");

            String htmlConteudo = lerConteudoHtml("email_confirmacao_aluguel.html");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

            htmlConteudo = htmlConteudo.replace("**{NOME_CLIENTE}**", aluguel.getCliente().getNome());
            htmlConteudo = htmlConteudo.replace("**{NUMERO_ARMARIO}**", aluguel.getArmario().getNumeroDoArmario());
            htmlConteudo = htmlConteudo.replace("**{LOCALIZACAO_NOME}**", "Nome da Localização aqui");
            htmlConteudo = htmlConteudo.replace("**{DATA_INICIO}**", aluguel.getDataHoraInicio().format(formatter));
            htmlConteudo = htmlConteudo.replace("**{CODIGO_ACESSO}**", aluguel.getCodigoAcesso());
            htmlConteudo = htmlConteudo.replace("**{ANO_ATUAL}**", String.valueOf(LocalDateTime.now().getYear()));

            helper.setText(htmlConteudo, true);
            javaMailSender.send(mimeMessage);
            logger.info("Email de confirmação de aluguel enviado para: {}", aluguel.getCliente().getEmail());
        } catch (Exception e) {
            logger.error("Erro ao enviar email de confirmação de aluguel: {}", e.getMessage());
        }
    }
}
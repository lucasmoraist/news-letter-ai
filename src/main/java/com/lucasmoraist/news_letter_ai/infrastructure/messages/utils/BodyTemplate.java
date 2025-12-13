package com.lucasmoraist.news_letter_ai.infrastructure.messages.utils;

import com.lucasmoraist.news_letter_ai.domain.model.Notice;

import java.util.List;
import java.util.stream.Collectors;

public final class BodyTemplate {

    private BodyTemplate() {
        throw new UnsupportedOperationException("Utility class");
    }

    private static final String NEWS_ITEM_TEMPLATE = """
            <div style="border-bottom:1px solid #e0e0e0; padding:16px 0;">
                <h2 style="margin:0 0 8px; font-size:18px; color:#0d6efd;">
                    %s
                </h2>
                <p style="margin:0 0 12px; font-size:14px; color:#333333; line-height:1.6;">
                    %s
                </p>
                <a href="%s" target="_blank"
                   style="font-size:14px; color:#0d6efd; text-decoration:none; font-weight:bold;">
                    Ler notícia completa →
                </a>
            </div>
            """;

    public static String buildEmail(List<Notice> notices, String introduction) {
        String newsBlock = notices.stream()
                .map(notice -> NEWS_ITEM_TEMPLATE.formatted(
                        notice.title(),
                        notice.content(),
                        notice.originalUrl()
                ))
                .collect(Collectors.joining());

        String emailBody = """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <title>Resumo Diário de Investimentos</title>
                </head>
                <body style="margin:0; padding:0; background-color:#f4f6f8; font-family: Arial, Helvetica, sans-serif;">
                
                    <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#f4f6f8; padding:20px;">
                        <tr>
                            <td align="center">
                                <table width="600" cellpadding="0" cellspacing="0" style="background-color:#ffffff; border-radius:8px; overflow:hidden;">
                
                                    <!-- Header -->
                                    <tr>
                                        <td style="background-color:#0d6efd; padding:24px; text-align:center; color:#ffffff;">
                                            <h1 style="margin:0; font-size:24px;">📈 Resumo Diário de Investimentos</h1>
                                            <p style="margin:8px 0 0; font-size:14px;">
                                                As 5 principais notícias para você começar o dia bem informado
                                            </p>
                                        </td>
                                    </tr>
                
                                    <!-- Intro -->
                                    <tr>
                                        <td style="padding:24px; color:#333333;">
                                            <p style="margin:0 0 16px; font-size:15px;">
                                                %s
                                            </p>
                                        </td>
                                    </tr>
                
                                    <!-- News Section -->
                                    <tr>
                                        <td style="padding:0 24px 24px;">
                
                                            {{NEWS_BLOCK}}
                
                                        </td>
                                    </tr>
                
                                    <!-- Footer -->
                                    <tr>
                                        <td style="background-color:#f4f6f8; padding:20px; text-align:center; color:#666666; font-size:12px;">
                                            <p style="margin:0;">
                                                Você está recebendo este e-mail porque é assinante do nosso conteúdo.
                                            </p>
                                            <p style="margin:8px 0 0;">
                                                © 2025 • Resumo Diário de Investimentos
                                            </p>
                                        </td>
                                    </tr>
                
                                </table>
                            </td>
                        </tr>
                    </table>
                
                </body>
                </html>
                """.formatted(introduction);

        return emailBody.replace("{{NEWS_BLOCK}}", newsBlock);
    }

}

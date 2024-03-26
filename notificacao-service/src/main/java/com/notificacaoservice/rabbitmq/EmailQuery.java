package com.notificacaoservice.rabbitmq;


import dto.PontoDispatcherMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class EmailQuery {


    private final EmailService emailService;
    private static final Logger log = LoggerFactory.getLogger(EmailQuery.class);


    public Mono<Void> messageOpenPoint(PontoDispatcherMessage ponto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


        String mensagem = getStringOpenPoint(ponto);


        return Mono.fromRunnable(() -> emailService.sendEmail(ponto.emailfuncionario(),
                "Este é um email do registro do ponto", mensagem)).then();
    }


    Function<PontoDispatcherMessage, String> messageFromExit = (pontoDispatch) -> {

        if (pontoDispatch.tipoContrato().equalsIgnoreCase("mensalista")) {
            String mensagem = getStringMensalista(pontoDispatch);
            return mensagem;
        } else {

            String mensagem = getStringHorista(pontoDispatch);

            return mensagem;
        }
    };


    public Mono<String> messageExit(PontoDispatcherMessage ponto) {

        return Mono.fromSupplier(() -> messageFromExit.apply(ponto));
    }

    public Mono<Void> messageClosePoint(PontoDispatcherMessage ponto) {
        return messageExit(ponto)
                .flatMap(message -> Mono.fromRunnable(() ->
                        emailService.sendEmail(ponto.emailfuncionario(),
                                "Este é um email do registro do ponto", message).join())).then();
    }

    public String calcularHoraAlmocoAndSaida(PontoDispatcherMessage ponto) {

        Function<PontoDispatcherMessage, String> horaAlmocoAndSaida = (pontoDispatch) -> {

            if (pontoDispatch.tipoContrato().equalsIgnoreCase("mensalista")) {
                // Calcula a hora da saida


                // Calcular o horario do almoço
                Duration duration = Duration.between(LocalTime.MIN,
                        pontoDispatch.horaentrada());

                LocalTime almoco = pontoDispatch.horaentrada().plus(duration.dividedBy(2));

                String almocoAndSaida = "Horário de almoço: " + getHourFormatter().format(almoco)
                        + "\n\nRetorno almoço: " + getHourFormatter().format(almoco.plusHours(1));
                return almocoAndSaida;
            }
            return "Funcionario horista, não contém horário de almoço e saida definida.";
        };

        return horaAlmocoAndSaida.apply(ponto);
    }


    private DateTimeFormatter getHourFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return formatter;
    }

    private DateTimeFormatter getDateTimeFormatter() {
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatterData;
    }

    private String getStringOpenPoint(PontoDispatcherMessage ponto) {
        LocalTime saida = ponto.horaentrada().
                plusHours(ponto.cargaDiaria().getHour());
        String mensagem = String.format(
                """
                        <html>
                            <body style="font-family: Arial, sans-serif;">
                                <h1>Ponto Registrado</h1>
                                <table border="1" style="padding: 10px">
                                     <tr>
                                        <td><b>Data:</b></td>
                                        <td>%s</td>
                                    </tr>
                                    <tr>
                                        <td><b>Matricula:</b></td>
                                        <td>%s</td>
                                    </tr>
                                    <tr>
                                        <td><b>Email:</b></td>
                                        <td>%s</td>
                                    </tr>
                                    <tr>
                                        <td><b>Hora da entrada:</b></td>
                                        <td>%s</td>
                                    </tr>
                                    <tr>
                                        <td><b>Hora do almoço:</b></td>
                                        <td>%s</td>
                                    </tr> <tr>
                                        <td><b>Hora da saida:</b></td>
                                        <td>%s</td>
                                    </tr>
                                   
                                </table>
                            </body>
                        </html>
                        """,
                ponto.data().format(getDateTimeFormatter()), ponto.funcionario(), ponto.emailfuncionario(), ponto.horaentrada().format(getHourFormatter()),
                calcularHoraAlmocoAndSaida(ponto), (ponto.tipoContrato().equalsIgnoreCase("mensalista") ? saida : "")

        );
        return mensagem;
    }

    private String getStringHorista(PontoDispatcherMessage pontoDispatch) {
        String mensagem = String.format(
                """
                        <html>
                            <body style="font-family: Arial, sans-serif;">
                                <h1>Ponto Registrado</h1>
                                <table border="1" style="padding: 10px">
                                    <tr>
                                        <td><b>Data:</b></td>
                                        <td>%s</td>
                                    </tr>
                                    <tr>
                                        <td><b>Matricula:</b></td>
                                        <td>%s</td>
                                    </tr>
                                    <tr>
                                        <td><b>Email:</b></td>
                                        <td>%s</td>
                                    </tr>
                                    <tr>
                                        <td><b>Tipo de contrato:</b></td>
                                        <td>%s</td>
                                    </tr>
                                    <tr>
                                        <td><b>Hora da entrada:</b></td>
                                        <td>%s</td>
                                    </tr>
                                    <tr>
                                        <td><b>Hora da saída:</b></td>
                                        <td>%s</td>
                                    </tr>
                                    
                                    <tr>
                                        <td><b>Salário do dia:</b></td>
                                        <td>%s</td>
                                    </tr>
                                    <tr>
                                        <td><b>Salário do mês:</b></td>
                                        <td>%s, até o momento</td>
                                    </tr>
                                    <tr>
                                        <td><b>Horas trabalhadas:</b></td>
                                        <td>%s</td>
                                    </tr>
                                </table>
                            </body>
                        </html>
                        """,
                pontoDispatch.data().format(getDateTimeFormatter()),
                pontoDispatch.funcionario(), pontoDispatch.emailfuncionario(),
                pontoDispatch.tipoContrato(),
                pontoDispatch.horaentrada().format(getHourFormatter()),
                pontoDispatch.horasaida().format(getHourFormatter()),
                pontoDispatch.salariodia(), pontoDispatch.salariomes(),
                pontoDispatch.horastrabalhada()
        );
        return mensagem;
    }

    private String getStringMensalista(PontoDispatcherMessage pontoDispatch) {
        String mensagem = String.format(
                """
                        <html>
                            <body style="font-family: Arial, sans-serif;">
                                <h1>Ponto Registrado</h1>
                                <table border="1" style="padding: 10px">
                                    <tr>
                                        <td><b>Data:</b></td>
                                        <td>%s</td>
                                    </tr>
                                    <tr>
                                        <td><b>Matricula:</b></td>
                                        <td>%s</td>
                                    </tr>
                                    <tr>
                                        <td><b>Email:</b></td>
                                        <td>%s</td>
                                    </tr>
                                    <tr>
                                        <td><b>Tipo de contrato:</b></td>
                                        <td>%s</td>
                                    </tr>
                                    <tr>
                                        <td><b>Hora da entrada:</b></td>
                                        <td>%s</td>
                                    </tr>
                                    <tr>
                                        <td><b>Hora da saída:</b></td>
                                        <td>%s</td>
                                    </tr>
                                    
                                    
                                </table>
                            </body>
                        </html>
                        """,
                pontoDispatch.data().format(getDateTimeFormatter()),
                pontoDispatch.funcionario(), pontoDispatch.emailfuncionario(), pontoDispatch.tipoContrato(),
                pontoDispatch.horaentrada().format(getHourFormatter()),
                pontoDispatch.horasaida().format(getHourFormatter())
        );
        return mensagem;
    }


}

package br.rmginner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

@SpringBootApplication
@EnableJms
public class Application {

    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.
        return factory;
    }

    @Bean // Serialize message content to json using TextMessage
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");

        return converter;
    }

    public static void main(String[] args) {
        // Launch the application
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        Scanner sc = new Scanner(System.in);

        System.out.println("Você é um vendedor ou um comprador?");

        boolean continuar = true;

        while (continuar){
            String tipoUsuarioText = sc.nextLine().trim();

            TipoUsuario tipoUsuario = TipoUsuario.valueOf(tipoUsuarioText.toUpperCase());

            if(Objects.isNull(tipoUsuario)){
                System.out.println("Tipo de usuário inválido, informe apenas 'vendedor' ou 'comprador'");
            }else{
                System.out.println("Informe o seu nome:\n");
                String userName = sc.nextLine();

                System.out.println("Informe o nome do produto:\n");
                String productName = sc.nextLine();

                if(tipoUsuario.equals(TipoUsuario.VENDEDOR)) {
                    jmsTemplate.convertAndSend(
                            "anuncio-item",
                            new Vendedor(userName,productName)
                    );

                    System.out.println("Deseja continuar?");

                    while (){

                    }
                }else{
                    System.out.println("Informe o nome do vendedor:\n");
                    String sellerName = sc.nextLine();

                    System.out.println("Informe o valor do lance:\n");

                    boolean invalidPrice = true;
                    Double price = null;

                    try{
                        price = sc.nextDouble();
                        invalidPrice = false;
                    }catch (InputMismatchException e){
                        System.out.println("O valor é inválido!:\n");
                    }

                    while(invalidPrice) {
                        try{
                            price = sc.nextDouble();
                            invalidPrice = false;
                        }catch (InputMismatchException e){
                            invalidPrice = true;
                            System.out.println("O valor é inválido!\n");
                            System.out.println("Informe o valor do lance:\n");
                        }
                    }

                    jmsTemplate.convertAndSend(
                            "compra-item",
                            new Comprador(userName,sellerName,productName,price)
                    );
                }

            }

        }
    }

}
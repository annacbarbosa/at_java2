package org.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import javax.swing.*;
import  java.awt.*;
import  java.awt.event.ActionEvent;
import  java.awt.event.ActionListener;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Main
{
    public static void main(String[] args)
    {
        String path = "http://localhost:8080/cartao";

        JFrame frame = new JFrame("Acesso Cliente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JTextField Id = new JTextField();
        JTextField acesso = new JTextField();
        acesso.setFont((new Font("Arial", Font.PLAIN, 30)));

        Id.setFont((new Font("Arial", Font.PLAIN, 30)));

        JLabel label1 = new JLabel("Id:");
        label1.setFont((new Font("Arial", Font.BOLD, 30)));
        JLabel label3 = new JLabel("Acesso:");
        label3.setFont((new Font("Arial", Font.BOLD, 30)));


        panel.add(label1);
        panel.add(Id);
        panel.add(label3);
        panel.add(acesso);

        String [] buttonLabels = {
                "Enviar","Sair"
        };

        for(String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            panel.add(button);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    double id;

                    try{
                        id = Double.parseDouble(Id.getText());

                        if(label.equals("Enviar"))
                        {
                            String content="{ \"id\": \" " + id + "\", \"" +  " \" }";

                            System.out.println(content);

                            URL url = new URL(path);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setRequestProperty("Content-Type","application/json");
                            connection.setDoOutput(true);

                            DataOutputStream out = new DataOutputStream( connection.getOutputStream() );
                            out.writeBytes(content);
                            out.flush();
                            out.close();
                            int responseCode = connection.getResponseCode();
                            System.out.println("Code: " + responseCode);
                            if (responseCode != HttpURLConnection.HTTP_OK)
                            {
                                System.out.println("Got an unexpected response code");
                            }


                            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String line;
                            while ((line = in.readLine()) != null) {
                                System.out.println(line);
                                JsonElement jsonElement = JsonParser.parseString(line);
                                JsonObject jsonObject = jsonElement.getAsJsonObject();
                                String serverAcesso = jsonObject.get("id").getAsString();

                                acesso.setText(serverAcesso);

                                if (Double.parseDouble(serverAcesso) == id) {
                                    acesso.setBackground(Color.GREEN);
                                } else {
                                    acesso.setBackground(Color.RED);
                                }
                            }
                            in.close();
                        }

                        if(label.equals("Sair"))
                        {
                            System.exit(0);
                        }


                    } catch(NumberFormatException ex){
                        acesso.setText("Erro: Entrada invalida");
                    } catch (ProtocolException ex) {
                        throw new RuntimeException(ex);
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
        frame.add(panel);
        frame.setVisible(true);
    }
}

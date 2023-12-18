package org.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import static spark.Spark.*;


public class Main {
    static int acessos=0;
    public static void main(String[] args)
    {
        port(8080);

        JFrame frame = new JFrame("Servidor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JTextField Id = new JTextField();
        JTextField acesso = new JTextField();

        Id.setFont((new Font("Arial", Font.PLAIN, 30)));
        acesso.setFont((new Font("Arial", Font.PLAIN, 30)));

        JLabel label1 = new JLabel("Id:");
        label1.setFont((new Font("Arial", Font.BOLD, 30)));
        JLabel label3 = new JLabel("Acesso:");
        label3.setFont((new Font("Arial", Font.BOLD, 30)));

        panel.add(label1);
        panel.add(Id);
        panel.add(label3);
        panel.add(acesso);

        String [] buttonLabels = {"Limpar","Fechar"};

        get("/cartao", (req, res) ->
        {
            String op1 = req.queryParams("p");
            Id.setText(op1);

            String content=Integer.toString(acessos);
            acessos++;
            return content;
        });

        post("/cartao", (req, res) -> {
            double id;

            String corpoRequisicao = req.body();
            System.out.println("Corpo JSON: " + corpoRequisicao);

            JsonElement jsonElement = JsonParser.parseString(corpoRequisicao);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Id.setText(jsonObject.get("Id").getAsString());
            acesso.setText(jsonObject.get("acesso").getAsString());

            id=Double.parseDouble(acesso.getText());

            acesso.setText(String.format(String.valueOf(id)));

            return "{\"acesso\":\"" +  String.format(String.valueOf(id)) + "\"}";
        });

        for(String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            panel.add(button);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if(label.equals("Fechar"))
                    {
                        System.exit(0);
                    }
                    if(label.equals("Limpar"))
                    {
                        Id.setText("");
                        acesso.setText("");
                    }
                }
            });
        }
        frame.add(panel);
        frame.setVisible(true);
    }
}

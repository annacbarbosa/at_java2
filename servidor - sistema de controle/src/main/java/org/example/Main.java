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
    static int acesso=1234;
    public static void main(String[] args)
    {
        port(8080);

        JFrame frame = new JFrame("Servidor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JTextField Id = new JTextField();
        JTextField acesso = new JTextField();

        Id.setFont((new Font("Arial", Font.PLAIN, 30)));
        acesso.setFont((new Font("Arial", Font.PLAIN, 30)));
        JLabel label1 = new JLabel("Código de Acesso:");
        label1.setFont((new Font("Arial", Font.BOLD, 30)));
        JLabel label3 = new JLabel("Acesso:");
        label3.setFont((new Font("Arial", Font.BOLD, 30)));

        panel.add(label1);
        panel.add(Id);
        panel.add(label3);
        panel.add(acesso);

        String [] buttonLabels = {"Limpar","Fechar"};

//        get("/nome", (req, res) ->
//        {
//            String op1 = req.queryParams("p");
//            Id.setText(Integer.toString(acessos));
//
//            String content=Integer.toString(acessos);
//            acessos++;
//            return content;
//        });

        post("/api",
                (req, res) -> {
                    double id, a;

                    String corpoRequisicao = req.body();
                    System.out.println("Corpo JSON: " + corpoRequisicao);

                    JsonElement jsonElement = JsonParser.parseString(corpoRequisicao);
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    Id.setText(jsonObject.get("Id").getAsString());
                    a = Double.parseDouble(Id.getText());


                    if (Id == acesso) {
                        acesso.setText("AK-1");
                        acesso.setForeground(Color.GREEN);
                        Timer timer = new Timer(3000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                acesso.setText("");
                                acesso.setForeground(Color.BLACK);
                            }
                        });
                        timer.setRepeats(false);
                        timer.start();
                    } else if (Id != acesso) {
                        acesso.setText("AK-0");

                        acesso.setForeground(Color.RED);
                    }
                    Timer timer = new Timer(3000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            acesso.setText("");
                            acesso.setForeground(Color.BLACK);
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                    return null;
                }


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
};
}
/*
 * Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * -Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in
 *  the documentation and/or other materials provided with the
 *  distribution.
 *
 * Neither the name of Oracle nor the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR
 * ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 */
package example.hello;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class Client {

    private static void tutorial() {
        System.out.println("Tutorial:");
        System.out.println("1. Ver vetor completo: mostra todos os elementos.");
        System.out.println("2. Ver elemento por índice: mostra o valor em um índice específico.");
        System.out.println("3. Adicionar elemento: adiciona um valor ao final do vetor.");
        System.out.println("4. Remover elemento: remove o valor em um índice específico.");
        System.out.println("5. Limpar vetor: remove todos os elementos.");
        System.out.println("6. Executar demonstração: executa operações automáticas.");
        System.out.println("0. Sair.");
    }

    private static void demo(Hello stub) throws Exception {
        System.out.println("Executando demonstração automática...");
        stub.clearVector();
        stub.addElement(10);
        stub.addElement(20);
        stub.addElement(30);
        System.out.println("Vetor após adicionar 10, 20, 30: " + stub.getVector());
        stub.removeElement(1);
        System.out.println("Vetor após remover índice 1: " + stub.getVector());
        stub.clearVector();
        System.out.println("Vetor após limpar: " + stub.getVector());
    }

    public static void main(String[] args) {
        System.out.println("Initiating client");

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            System.out.println("Registry has been located");
            Hello stub = (Hello) registry.lookup("Hello");
            System.out.println("Found server");

            Scanner scanner = new Scanner(System.in);
            int option;
            do {
                System.out.println("\nMenu:");
                System.out.println("1. Ver vetor completo");
                System.out.println("2. Ver elemento por índice");
                System.out.println("3. Adicionar elemento");
                System.out.println("4. Remover elemento");
                System.out.println("5. Limpar vetor");
                System.out.println("6. Tutorial");
                System.out.println("7. Executar demonstração");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        List<Integer> vector = stub.getVector();
                        System.out.println("Vetor: " + vector);
                        break;
                    case 2:
                        System.out.print("Índice: ");
                        int idx = scanner.nextInt();
                        try {
                            System.out.println("Elemento: " + stub.getElement(idx));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage());
                        }
                        break;
                    case 3:
                        System.out.print("Valor para adicionar: ");
                        int val = scanner.nextInt();
                        stub.addElement(val);
                        System.out.println("Adicionado.");
                        break;
                    case 4:
                        System.out.print("Índice para remover: ");
                        int remIdx = scanner.nextInt();
                        try {
                            stub.removeElement(remIdx);
                            System.out.println("Removido.");
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage());
                        }
                        break;
                    case 5:
                        stub.clearVector();
                        System.out.println("Vetor limpo.");
                        break;
                    case 6:
                        tutorial();
                        break;
                    case 7:
                        demo(stub);
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } while (option != 0);

            scanner.close();

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
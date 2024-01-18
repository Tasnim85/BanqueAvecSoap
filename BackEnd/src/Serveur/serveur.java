package serveur;

import javax.xml.ws.Endpoint;

import service.banque_service;

public class serveur {
	 public static void main(String[] args) {
	Endpoint.publish("http://localhost:8080/", new banque_service());
		 System.out.println("serveur en cour");
}}

package com.companydomain.serverdemo.sender;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Server {
	
	// Reading the file location from application.properties
	@Value("${fileLocation}")
	private String fileLocation;
		
	// Reading port number from application.properties
	@Value("${port}")
	private int port;

	public void start() {
		ServerSocketChannel serverSocketChannel = null;
		SocketChannel socketChannel = null;
		RandomAccessFile file = null;
		try {
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress(port));
			while(true) {
				socketChannel = serverSocketChannel.accept();
				System.out.println("Connection established with " + socketChannel.getRemoteAddress());
				file = new RandomAccessFile(fileLocation, "r");
				ByteBuffer buffer = ByteBuffer.allocate(10240); // allocating buffer size as 10KB
				FileChannel fileChannel = file.getChannel();
				while (fileChannel.read(buffer) > 0) {
					buffer.flip();
					socketChannel.write(buffer);
					buffer.clear();
				}
				fileChannel.close();
				System.out.println("File Sent on "+LocalDateTime.now());
				socketChannel.close();
			}
		 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
}}

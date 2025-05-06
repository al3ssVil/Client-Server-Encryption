# Encrypted Client-Server Communication

## Description

This Java project implements a basic encrypted client-server communication system using the Diffie-Hellman key exchange to establish a shared secret. Once connected, users can send encrypted messages using either the Caesar cipher or the columnar transposition cipher.

## Features
Secure key exchange via Diffie-Hellman

Two encryption methods:

- Caesar Cipher (shift based on shared key)

- Columnar Transposition Cipher (key-based matrix encryption)

Handles unexpected disconnections gracefully

Input validation and error handling

## How It Works
Connection: The client connects to the server over a socket.

Key Exchange: Both sides generate and exchange public values using Diffie-Hellman. They compute the same shared secret key.

Encryption Options:

- Caesar Cipher: Message is shifted by the secret key.

- Transposition Cipher: Message is rearranged in a matrix using a key (all letters must be unique).

Message Exchange: Encrypted message is sent and decrypted on the server side.

## Requirements
Java 11 or later

## Notes
If the client provides an invalid key (e.g., duplicate characters for transposition), it will be asked to enter a new one.

The system detects and handles disconnections properly.

# S-AES implementation in Java

# - Ankit Mishra

This program uses two .java files, one for each encryption and decryption using the
S_AES algorithm that uses a 16-bit plaintext and a 16-bit cipher key for encryption.
An example of the terminal output (for both client and server) is included in the zip file.
The class files can be run directly on a terminal using a java compiler to replicate the
output from both the client and server applications respectively (that are shown in the
pictures).
The following files are used in this program :

## Client (.java)

The client file accepts a text input as well as a 16-bit binary key input for encryption.
The text input is first converted into a binary stream and then padded with 0s to make its
size divisible by 16.
Each 16-bit chunk is encrypted separately (using the S_AES function) and for each
16-bit chunk, the encryption is shown step by step to maintain readability. After every
16-bit chunk is encrypted, they are concatenated to form the final ciphertext.
The ciphertext, key and the padding data is sent to the server. (Note : In a real
implementation, the key should be sent through a different secure channel but here we
are sending it from the same channel). The client receives the plaintext back from the
server after decryption to imply successful encryption and decryption. The following
functions were used in the client.java file :

## 1. String StringXOR(String a, String b)

This function accepts two binary strings and returns the xor of those two strings.

## 2. String RotW(String k)

This function performs the RotWord operation on a word (group of two nibbles in this
case, word will be used to define a group of two nibbles further in this document).
Essentially it just swaps the two nibbles consisting of the word.


## 3. String SubW(String k)

This function performs the SubWord operation on a word (performing an s-box
transformation on both nibbles in the word using a lookup table).

## 4. String SubNib(String k)

This function performs an s-box transformation on a nibble using a lookup table.

## 5. String multiply4(String k)

This function multiplies a nibble with 4 in gf(16) modulo x^4 + x + 1 using a lookup table.

## 6. String S_AES(String pt_b, String key)

This is the most important function that encrypts a 16-bit binary plaintext into a 16-bit
binary ciphertext using the S-AES encryption algorithm with the help of a 16-bit secret
key.

## 7. String S2B(String S)

This function converts a text (UTF-8 encoded) into a binary stream.

# Server (.java)

The server file receives the ciphertext, key and padding data is separated. The
ciphertext is separated into 16-bit chunks and each 16-bit chunk is decrypted separately
(using the AES_Decryption function) and for each 16-bit chunk, the decryption is shown
step by step. After all the chunks are decrypted, they are concatenated, the padding is
removed, and it is converted back to text to reveal the original plaintext that was
provided by the user at the client side. The original plaintext is then sent back to the
client.
The following functions were used in this file :

## 1. String StringXOR(String a, String b)

This function accepts two binary strings and returns the xor of those two strings.

## 2. String RotW(String k)

This function performs the RotWord operation on a word (group of two nibbles in this
case, word will be used to define a group of two nibbles further in this document).
Essentially it just swaps the two nibbles consisting of the word.

## 3. String SubW(String k)

This function performs the SubWord operation on a word (performing an s-box
transformation on both nibbles in the word).

## 4. String SubNib(String k)

This function performs an s-box transformation on a nibble.

## 5. String multiply9(String k)

This function multiplies a nibble with 9 in gf(16) modulo x^4 + x + 1 using a lookup table.

## 6. String multiply2(String k)

​This function multiplies a nibble with 9 in gf(16) modulo x^4 + x + 1 using a lookup table.

## Sample run :




![image](https://user-images.githubusercontent.com/55701343/111283959-a8fe7780-8665-11eb-9ad6-3efaf42ec08e.png)

![image](https://user-images.githubusercontent.com/55701343/111283986-b156b280-8665-11eb-8e1b-f4457debd29e.png)


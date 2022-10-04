# Custom Enigma

This project implements the Wehrmacht Enigma M3 for educational purposes.

**Rotor** set is different from the one used in Enigma M3, this implementation uses following rotors:

* ROTOR_I
  * Ring `FKQHTLXOCBJSPDZRAMEWNIUYGV`
  * Notch `H`
* ROTOR_II
  * Ring `SLVGBTFXJQOHEWIRZYAMKPCNDU`
  * Notch `M`
* ROTOR_III
  * Ring `EHRVXGAOBQUSIMZFLYNWKTPDJC`
  * Notch `V`
* ROTOR_IV
  * Ring `NTZPSFBOKMWRCJDIVLAEYUXHGQ`
  * Notch `M`
* ROTOR_V
  * Ring `BDFHJLCPRTXVZNYEIWGAKMUSQO`
  * Notch `D`

**Reflector** is also different from the default UKW-B used by Enigma M3:

* `LE:YJ:VC:NI:XW:PB:QM:DR:TA:KZ:GF:UH:OS`

**Plugboard** is accepting default 10 plug cables configuration.

## Complexity

Since there are changes in Enigma configuration, complexity is still in the same range:

* Combinations of 3 rotors out of 5 = `(5 x 4 x 3) = 60`
* Each ring can be set in any of 26 positions initially = `(26 x 26 x 26) = 17,576`
* Notch combinations (most-left rotor is excluded) = `(26 x 26) = 676`
* Plugboard combinations for 10 cables = `26! / (26 - 2 · 10)! · 10! · 2 · 10 = 150,738,274,937,250`

Complexity is equals to `60 x 17,576 x 676 x 150,738,274,937,250 = 107,458,687,327,250,619,360,000`

That can be expressed as `1.07 x 10^23`, and it's comparable with a **77 bit key**

Additional details on Enigma classical configuration available in [https://www.ciphermachinesandcryptology.com/en/enigmatech.htm](https://www.ciphermachinesandcryptology.com/en/enigmatech.htm)

>> The goal of this project is to provide a cipher algorithm with the same complexity of Enigma M3 but with different initial settings. Hence, traditional cryptanalysis attacks can be performed on it but no standard program can be used as is.

## Building the source code

**Requirements**

* [Maven](https://maven.apache.org)
* [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

Type following command from the root folder of the project:

```
$ mvn clean package
```

Executable JAR Application `custom-enigma-0.8.0.jar` will be produced in `target` folder.

## Running the application

Create a text file including the plain text to be ciphered.

```
$ vi plaintext.txt
Every secret creates a potential failure point
```

Run the program create a `plugboard`, the `rotor` configuration and providing the name of the output file.

```
$ java -jar target/custom-enigma-0.8.0.jar \
    --input-file=plaintext.txt \
    --plugboard=IR:HQ:NT:WZ:VC:OY:GP:LF:BX:AK \
    --left-rotor=1   --left-rotor-position=0 \
    --middle-rotor=2 --middle-rotor-position=0 \
    --right-rotor=3  --right-rotor-position=0 \
    --output-file=cipher.txt
```

The program will produce the file `cipher.txt` including the ciphered text from the plain text.

```
$ cat cipher.txt
ALFDP AFVFBZ AESMZYQ W YDWKCFNZS MYSBLXN TWCSP
```

Use the same configuration to get *plain text* from `cipher.txt`.

```
$ java -jar target/custom-enigma-0.8.0.jar \
    --input-file=cipher.txt \
    --plugboard=IR:HQ:NT:WZ:VC:OY:GP:LF:BX:AK \
    --left-rotor=1   --left-rotor-position=0 \
    --middle-rotor=2 --middle-rotor-position=0 \
    --right-rotor=3  --right-rotor-position=0 \
    --output-file=deciphered.txt

$ cat deciphered.txt
EVERY SECRET CREATES A POTENTIAL FAILURE POINT    
```


# License

    Copyright 2022 Angel Borroy

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

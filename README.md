# Kalah Game

[![Build Status](https://travis-ci.org/eacarvalho/kalah-game.svg?branch=master)](https://travis-ci.org/eacarvalho/kalah-game)

A funny math game based on [Kalah Game](https://en.wikipedia.org/wiki/Kalah)

About the game
---
Each of the two players has six pits in front of him/her. To the right of the six pits, each player has a larger pit, his Kalah or house. At the start of the game, six stones are put In each pit.
The player who begins picks up all the stones in any of their own pits, and sows the stones on to the right, one in each of the following pits, including his own Kalah. No stones are put in the opponent's' Kalah. If the players last stone lands in his own Kalah, he gets another turn. This can be repeated any number of times before it's the other player's turn.
when the last stone lands in an own empty pit, the player captures this stone and all stones in the opposite pit (the other players' pit) and puts them in his own Kalah.
The game is over as soon as one of the sides run out of stones. The player who still has stones in his/her pits keeps them and puts them in his/hers Kalah. The winner of the game is the player who has the most stones in his Kalah.

Introduction
---
Kalah Game provides REST services for create, update and search for boards allowing implement the game through an API or even play the game through web accessing the URL.

Technologies
---
Built on **maven**, **java 8**, spring-boot 1.4.1, spring-boot-actuator, spring-mvc and lombok. 

> Maven and Java 8 are required to run the project, be sure you have already installed.

Travis CI (Continuous Integration)
---
Using Travis as CI (https://travis-ci.org/eacarvalho/kalah-game)

How start the Kalah Game
---
1) Build:

```
$ mvn clean package
```

2) Start:

```
$ java -jar target/kalah-game-1.0.jar
```

How access the Kalah Game Web
---

http://localhost:8080

How access the Kalah Game API
---

- POST    http://localhost:8080/api/boards
- GET     http://localhost:8080/api/boards
- GET     http://localhost:8080/api/boards/d977a3e4-3aae-493d-b4aa-358fb2080e7e
- PUT     http://localhost:8080/api/boards/d977a3e4-3aae-493d-b4aa-358fb2080e7e/pits/ONE
- PUT     http://localhost:8080/api/boards/d977a3e4-3aae-493d-b4aa-358fb2080e7e/pits/TWO
- PUT     http://localhost:8080/api/boards/d977a3e4-3aae-493d-b4aa-358fb2080e7e/pits/THREE
- PUT     http://localhost:8080/api/boards/d977a3e4-3aae-493d-b4aa-358fb2080e7e/pits/FOUR
- PUT     http://localhost:8080/api/boards/d977a3e4-3aae-493d-b4aa-358fb2080e7e/pits/FIVE
- PUT     http://localhost:8080/api/boards/d977a3e4-3aae-493d-b4aa-358fb2080e7e/pits/SIX

Sample JSON' Board:

```
{
  "id" : "d977a3e4-3aae-493d-b4aa-358fb2080e7e",
  "playerOne" : {
    "id" : "ONE",
    "pits" : [ {
      "id" : "ONE",
      "stones" : 6
    }, {
      "id" : "TWO",
      "stones" : 6
    }, {
      "id" : "THREE",
      "stones" : 6
    }, {
      "id" : "FOUR",
      "stones" : 6
    }, {
      "id" : "FIVE",
      "stones" : 6
    }, {
      "id" : "SIX",
      "stones" : 6
    } ],
    "house" : {
      "stones" : 0
    }
  },
  "playerTwo" : {
    "id" : "TWO",
    "pits" : [ {
      "id" : "ONE",
      "stones" : 6
    }, {
      "id" : "TWO",
      "stones" : 6
    }, {
      "id" : "THREE",
      "stones" : 6
    }, {
      "id" : "FOUR",
      "stones" : 6
    }, {
      "id" : "FIVE",
      "stones" : 6
    }, {
      "id" : "SIX",
      "stones" : 6
    } ],
    "house" : {
      "stones" : 0
    }
  },
  "currentPlayer" : "ONE",
  "winner" : null,
  "createdDate" : 1477163575713
}
```

## How check health of the app (actuator)
- HEALTH      http://localhost:8080/health
- INFO        http://localhost:8080/info 

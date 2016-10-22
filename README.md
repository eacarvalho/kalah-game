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

How access the Kalah Game API (verbs and status)
---

- POST    http://localhost:8080/api/boards
> Create a new game board, allowing play the game (Http Status 200).

- GET     http://localhost:8080/api/boards
> Return the json of all boads saved (Http Status 200). If the there are no boards yeat the status code returned is 204.

- GET     http://localhost:8080/api/boards/{boardId}
> Return the JSON's board, a snapshot of that moment (Http Status 200). If the boardId doesn't exists the status code returned is 404.

- PUT     http://localhost:8080/api/boards/{boardId}/pits/{pitId}
> Move stones from the selected pit, as all movement are saved, the board has the snapshot of the last movement, so, it knows which player is and how many stones are in each pit and house (Http Status 200). If a pit has zero stone and you try to move the API will return status code 400.
> The API has 6 spot for pits, so the call possibilities are:
```
http://localhost:8080/api/boards/{boardId}/pits/ONE
http://localhost:8080/api/boards/{boardId}/pits/TWO
http://localhost:8080/api/boards/{boardId}/pits/THREE
http://localhost:8080/api/boards/{boardId}/pits/FOUR
http://localhost:8080/api/boards/{boardId}/pits/FIVE
http://localhost:8080/api/boards/{boardId}/pits/SIX
```

Sample JSON' Board:
---
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

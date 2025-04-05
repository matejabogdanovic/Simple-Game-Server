# Game Server in Java

It's a simple **tick-based game server** using Sockets.

## 📦 Java Package Structure

There are four Java packages in the project:

- `Server` – Contains classes that implement the server side.
- `Client` – Contains classes that implement the client side.
- `Shared` – Defines types that are shared between server and client. In this case, it's the 'Player' class. The server sends a 'Player[]' array – one 'Player' object for each connected player.
- `Game` – Represents the game itself, which the developer implements.

## 🚀 How It Works

1. **Start the server:**
   ```java
   Server server = new Server(int playerCnt, int port);
   server.start();
   ```
   This starts the server, opens the socket, and waits for the specified number of players to connect.

2. **Start the game and client:**
   The `Game` class represents the game logic and creates a client instance like this:
   ```java
   Client client = new Client(String host, int port, String name);
   client.start();
   ```
   The client is typically started after setting up the window and environment (AWT is used for GUI).

3. **Client threading model:**
   The client uses two threads:
   - **Reader thread** – receives updates from the server.
   - **Writer thread** – sends keyboard input to the server.

4. **Server threading and tick loop:**
   - The server uses a timer.
   - Between each tick:
     - It receives input from each client (defined in `Player.ValidInput`).
     - Updates the `Player` objects on the server side.
   - When the timer ticks:
     - The server applies the latest input to each player.
     - This ensures that only one X and/or Y coordinate change happens per tick, so movement is not dependent on how fast a client sends input.

5. **Server threads:**
   - For each client, the server spawns a **connection thread** that:
     - Performs authentication by receiving the player's name.
     - Starts two threads for that client:
       - **Reader thread** – receives input.
       - **Writer thread** – sends updated `Player[]` state to the client.
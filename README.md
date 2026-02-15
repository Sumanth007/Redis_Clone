# Redis Clone

A lightweight, educational implementation of Redis server in Java, featuring in-memory key-value storage with support for basic Redis commands and the Redis Serialization Protocol (RESP).

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [Supported Commands](#supported-commands)
- [Implementation Details](#implementation-details)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

## 🎯 Overview

This project is an educational Redis clone that implements core Redis functionality using Java. It demonstrates key concepts in distributed systems, network programming, and concurrent data structures. The server listens on the standard Redis port (6379) and communicates using the Redis Serialization Protocol (RESP), making it compatible with standard Redis clients.

**Key Highlights:**
- ✅ Multi-threaded TCP server supporting concurrent client connections
- ✅ In-memory key-value store with thread-safe operations
- ✅ RESP (Redis Serialization Protocol) implementation
- ✅ TTL (Time To Live) support for automatic key expiration
- ✅ Clean architecture with separation of concerns

## 🚀 Features

- **Multi-Client Support**: Handles multiple concurrent client connections using a thread-per-client model
- **Thread-Safe Storage**: Uses `ConcurrentHashMap` for safe concurrent access to stored data
- **TTL Support**: Keys can be set with expiration times using the `PX` (milliseconds) option
- **RESP Protocol**: Full support for Redis Serialization Protocol encoding/decoding
- **Configuration Management**: Command-line argument parsing for server configuration
- **Extensible Handler Pattern**: Easy to add new Redis commands through the handler pattern

## 🏗️ Architecture

### Design Patterns

1. **Singleton Pattern**: Used for `Storage` and `ServerConfig` to ensure single instances across the application
2. **Strategy Pattern**: Command handlers implement different strategies for handling Redis commands
3. **Template Method Pattern**: `BaseHandler` provides common structure for all handlers
4. **Thread-Per-Client Model**: Each client connection runs in its own thread

### Core Components

```
┌─────────────────┐
│   Main.java     │  ← Entry point, creates ServerSocket on port 6379
└────────┬────────┘
         │ spawns
         ▼
┌─────────────────┐
│ ClientHandler   │  ← Routes commands to appropriate handlers
└────────┬────────┘
         │ uses
         ▼
┌─────────────────────────────────────────────────┐
│  Handler Classes                                │
│  • SetHandler    • GetHandler                   │
│  • PingHandler   • EchoHandler                  │
│  • ConfigHandler                                │
└─────────────────┬───────────────────────────────┘
                  │ accesses
                  ▼
         ┌─────────────────┐
         │  Storage        │  ← Thread-safe ConcurrentHashMap
         │  (Singleton)    │
         └─────────────────┘
```

## 🛠️ Getting Started

### Prerequisites

- Java 8 or higher
- A Redis client (redis-cli, or any Redis-compatible client)

### Compilation

```bash
cd java
javac Main.java
```

### Running the Server

```bash
# Basic usage
java Main

# With configuration options
java Main --dir /path/to/data --dbfilename dump.rdb
```

The server will start listening on `tcp://localhost:6379`

### Connecting with redis-cli

```bash
redis-cli -p 6379
```

## 📝 Supported Commands

### Basic Commands

| Command | Syntax | Description | Example |
|---------|--------|-------------|---------|
| **PING** | `PING` | Returns PONG | `PING` → `PONG` |
| **ECHO** | `ECHO message` | Returns the message | `ECHO "Hello"` → `"Hello"` |
| **SET** | `SET key value [PX milliseconds]` | Set a key-value pair with optional TTL | `SET mykey "Hello"` → `OK` |
| **GET** | `GET key` | Get the value of a key | `GET mykey` → `"Hello"` |
| **CONFIG** | `CONFIG GET parameter` | Get configuration parameter | `CONFIG GET dir` |

### SET Command Options

- **Basic SET**: `SET key value` - Stores a key-value pair
- **SET with Expiry**: `SET key value PX milliseconds` - Stores with TTL in milliseconds

Example:
```bash
127.0.0.1:6379> SET session:123 "user_data" PX 10000
OK
127.0.0.1:6379> GET session:123
"user_data"
# After 10 seconds
127.0.0.1:6379> GET session:123
(nil)
```

## 🔍 Implementation Details

### Thread Safety

The project uses `ConcurrentHashMap` from `java.util.concurrent` to ensure thread-safe operations on the key-value store. This allows multiple client threads to read and write simultaneously without data corruption.

### TTL Implementation

TTL (Time To Live) is implemented using Java's `Timer` and `TimerTask`:
- When a key is set with a timeout, a timer task is scheduled
- The task automatically removes the key after the specified timeout
- Supports millisecond precision using the `PX` option

### RESP Protocol

The `Encoder` class implements RESP encoding for:
- **Simple Strings**: `+OK\r\n`
- **Bulk Strings**: `$5\r\nHello\r\n`
- **Arrays**: `*2\r\n$3\r\nGET\r\n$5\r\nmykey\r\n`
- **Null**: `$-1\r\n`

### Socket Configuration

- **Port**: 6379 (standard Redis port)
- **Socket Timeout**: 10 seconds
- **Reuse Address**: Enabled for quick restarts
- **I/O**: Buffered readers/writers for efficient communication

## 📁 Project Structure

```
Redis_Clone/
├── README.md
└── java/
    ├── Main.java                          # Server entry point
    ├── config/
    │   └── ServerConfig.java              # Configuration singleton
    ├── storage/
    │   └── Storage.java                   # Thread-safe in-memory store
    └── client/
        ├── ClientHandler.java             # Client connection handler
        ├── Encoder.java                   # RESP protocol encoder
        ├── ResponseConstants.java         # Response string constants
        └── handlers/
            ├── BaseHandler.java           # Base handler class
            ├── IHandler.java              # Handler interface
            ├── PingHandler.java           # PING command handler
            ├── EchoHandler.java           # ECHO command handler
            ├── SetHandler.java            # SET command handler
            ├── GetHandler.java            # GET command handler
            ├── IOHelper.java              # I/O utility methods
            └── config/
                └── ConfigHandler.java     # CONFIG command handler
```

## 🤝 Contributing

Contributions are welcome! Here are some ways you can contribute:

1. **Add New Commands**: Implement additional Redis commands (DEL, EXISTS, INCR, etc.)
2. **Improve Performance**: Optimize data structures and algorithms
3. **Add Persistence**: Implement RDB or AOF persistence
4. **Enhanced Testing**: Add unit and integration tests
5. **Documentation**: Improve code comments and documentation

### Adding a New Command

1. Create a new handler class in `client/handlers/` extending `BaseHandler`
2. Implement the `handle()` method
3. Add command routing in `ClientHandler.java`

Example:
```java
public class DelHandler extends BaseHandler {
    public DelHandler(Socket socket, BufferedReader in, BufferedWriter out) {
        super(socket, in, out);
    }
    
    @Override
    public void handle() throws IOException {
        // Implementation
    }
}
```

## 📄 License

This project is open source and available for educational purposes.

---

**Note**: This is an educational project demonstrating Redis concepts. It is not intended for production use. For production applications, use the official Redis server.

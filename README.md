# CQShell 
It's a web based CQ commandline interpreter because every decent piece of software needs a shell!

## Supported shell commands

The following commands are currently available. 
All commands are organized in groups to make the delivery of man page information a bit more efficient.

### Group: Basic file operations

| Action | Description | Status |
|---|---|---|
| pwd | shows the current location | available |
| cd | changes directory | available |
| ls | lists the contents of the current location  | available |
| rm | deletes a node  | not yet implemented |
| touch | creates a node at the given position  | not yet implemented |

#### Examples
``` bash
rm name // files only
rm -R directory // files or directories

touch <name> [-t <type> -p {property:value}]
```

### Group: Basic system operations:
uname -a (number of available threads)
whoami
ping .... http ping another cq instance to check if it is available and reachable from this instance


### Basic operators:

&& 
|
><

### Group: Script Execution

### Group: OSGI
bundle status // all bundles shows installed, resolved, active
bundle status <name>|<id>
bundle status <name>|<id>

servlet list

### Group: Replication

### Group: Logging

### Group: Healthcheck

### Group: Query execution

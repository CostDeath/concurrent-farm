# Concurrent Farm

## Project Overview
The concurrent farm is a simulation of a farm where farmers and buyers are represented by threads. The project was
created as a paired assignment for a university final year module.

## Assignment Details
The assignment required us to demonstrate our knowledge of concurrent systems through the simulation of a farm. This
farm has different fields for different animals and farmers move arriving animals from an enclosure to the fields where
buyers can buy them.
- Farmers must each be their own threaded
- Buyers must each be their own thread
- Animals and buyers appear based on a random chance
- Only 1 farmer can unload into a field at a time
- Each buyer is only interested in 1 type of animal

For a full list of requirements, the [full specification](spec.md) for the assignment is available.

## Usage
### Running the program
In order to run the program, it must first be compiled. This can be done by running the following command in the `/src`
directory.
```sh
javac Main.java
```

Running the program begins the simulation. This can be done by running the following command in the `/src` folder.
```sh
java Main
```

### Configuring variables
Variables must be set before compilation. After changing any variables, the program must be recompiled.

Most variables can be updated through the `config.properties` file. The exception to this is the animals going through
the farm, which must be updated in the `Model/AnimalType.java` file.

## Known Issues
- [ ] High, incrementing thread names
- Due to a new thread being created each tick, there is never only the lower number threads alive. Due to this, new 
thread names are increasingly higher instead of resetting the count.
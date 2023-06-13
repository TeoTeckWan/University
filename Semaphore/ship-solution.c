// C program to demonstrate thread synchronization using semaphore
#include <stdio.h> 
#include <pthread.h> 
#include <unistd.h>
#include <semaphore.h> 

// Define the number of cargos, ships, trailers and loading bays
#define MAX_CARGO 8
#define NUM_SHIPS 3
#define NUM_TRAILERS 5
#define NUM_LOADING_BAYS 2

// Semaphore variable called dock, loading_bay, ship_signal, trailer_signal, cargo_loading are declared
sem_t dock;
sem_t loading_bay;
sem_t ship_signal;
sem_t trailer_signal;
sem_t cargo_loading;

// Global variable for indicating the total trailers currently in the bay
int trailers_in_bay = 0;
int storage_field = 0;
int cargo_loaded = 0;

// Thread ship does the following
void* ship(void* arg) {
    int ship_id = *(int*)arg;

    // Ship waits for available dock
    printf("Ship %d is waiting for available dock.\n", ship_id);
    sem_wait(&dock);
    cargo_loaded = 0;
    printf("Ship %d entered the dock.\n", ship_id);

    while (cargo_loaded < MAX_CARGO) {
        // Ship waits for trailer
        printf("Ship %d is waiting for trailer to be ready.\n", ship_id);
        sem_wait(&trailer_signal);

        // Signal trailer to start unloading cargo
        printf("Ship %d is signaling trailer to start unloading cargo.\n", ship_id);
        sem_post(&ship_signal);

        printf("Ship %d is unloading the cargo to the trailer.\n", ship_id);

        // Wait trailer finish unloading
        sem_wait(&cargo_loading);
    }

    // Ship leaves the port after unloading all the cargo.
    sem_post(&dock);
    printf("Ship %d leaves the port.\n", ship_id);
}

// Thread trailer does the following
void* trailer(void* arg) {
    int trailer_id = *(int*)arg;

    // Infinite Loop
    while (1) {
        // Trailer looks for the available loading bay
        printf("Trailer %d is waiting for available loading bay.\n", trailer_id);
        sem_wait(&loading_bay);

        // Trailer enters the loading bay
        printf("Trailer %d entered the loading bay.\n", trailer_id);
        trailers_in_bay++;

        // Trailer signals the ship for loading a cargo
        printf("Trailer %d is signaling ship for loading a cargo.\n", trailer_id);
        sem_post(&trailer_signal);

        // Trailer waits for signal from ship to load a cargo
        printf("Trailer %d is waiting for signal from ship to load a cargo.\n", trailer_id);
        sem_wait(&ship_signal);

        // Trailer loads the cargo and print out the total number of trailers in the loading bay
        printf("Trailer %d is loading a cargo from ship.\n", trailer_id);
        printf("Total trailers in the loading bay: %d\n", trailers_in_bay);
        sleep(2);

        // Trailer finish loading the cargo and departs from the loading bay to storage field
        cargo_loaded++;
        printf("Trailer %d finish loading the cargo\n", trailer_id);
        printf("Number of cargo that unloaded for current ship: %d \n", cargo_loaded);
        printf("Trailer %d departed from loading bay to storage field.\n", trailer_id);

        // Trailer signals to the ship that the cargo is successfully loaded.
        sem_post(&cargo_loading);

        trailers_in_bay--;
        storage_field++;
        sem_post(&loading_bay);

        // Trailer returns to the loading bay
        sleep(3);
        printf("Trailer %d returns to loading bay.\n", trailer_id);
    }
}

int main()
{
    // These values are used to identify thread number
    int ship_id[3] = { 1, 2, 3 };
    int trailer_id[5] = { 1, 2, 3, 4, 5 };

    // Initialize semaphore mutex value as 0 (3rd argument)
    sem_init(&dock, 0, 1);
    sem_init(&loading_bay, 0, NUM_LOADING_BAYS);
    sem_init(&ship_signal, 0, 0);
    sem_init(&trailer_signal, 0, 0);
    sem_init(&cargo_loading, 0, 0);

    // Intialize two variables type pthread_t called ships and trailers
    pthread_t ships[NUM_SHIPS];
    pthread_t trailers[NUM_TRAILERS];

    // Create 5 trailer threads
    for (int i = 0; i < NUM_TRAILERS; i++) {
        pthread_create(&trailers[i], NULL, trailer, &trailer_id[i]);
    }

    // Create 3 ship threads
    for (int i = 0; i < NUM_SHIPS; i++) {
        pthread_create(&ships[i], NULL, ship, &ship_id[i]);
        //Ship arrives every 4 seconds
        sleep(4);
    }

    // Join Ship threads after they finished running
    for (int i = 0; i < NUM_SHIPS; i++) {
        pthread_join(ships[i], NULL);
    }

    // Cancel all trailer threads after all ships had departed from dock
    for (int i = 0; i < NUM_TRAILERS; i++) {
        pthread_cancel(trailers[i]);
    }

    printf("Total cargo in storage field: %d \n", storage_field);

    // Destroy the semaphore after finished using it
    sem_destroy(&dock);
    sem_destroy(&loading_bay);
    sem_destroy(&trailer_signal);
    sem_destroy(&ship_signal);
    sem_destroy(&cargo_loading);

    return 0;
}

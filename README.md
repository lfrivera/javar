# JavaR

JavaR is a library that enables the integration between Java and R languages through Rserve (<https://www.rforge.net/Rserve/>).

## Prerequisites

*   R programming language (<https://www.r-project.org/about.html>).
*   X11 libraries **(Only on OS X systems)**. If not istalled, you can download it from: <https://www.xquartz.org/>.

## Configuration (Unix platforms)

The following steps are required in order to use the JavaR library:

*   Rserve installation

1.  **Start R:** type "R" on terminal. 
2.  **Install Rserve:** type "install.packages("Rserve")" on R console.

*   Starting up Rserve (Optional, JavaR can start Rserve automatically)

1.  **Start R:** type "R" on terminal.
2.  **Declare the usage of Rserve:** type "library('Rserve')" on R console.
3.  **Start Rserve daemon:** type "Rserve()" on R console.

*   Enabling automatic start of Rserve (Optional)

1.  **Start R:** type "R" on console.
2.  **Detect current Rserve library location:** type "system.file("libs", "Rserve", package="Rserve")" on R console. Copy the Rserve location (e.g., /home/lfrivera/R/x86_64-pc-linux-gnu-library/3.2/Rserve/libs/Rserve)
3.  **Create a symbolic link of Rserve in the R bin folder:** type the following command on terminal: "ln -s CURRENT_RSERVE_LIBRARY_LOCATION /usr/lib/R/bin/Rserve" (e.g., ln -s /home/lfrivera/R/x86_64-pc-linux-gnu-library/3.2/Rserve/libs/Rserve /usr/lib/R/bin/Rserve).





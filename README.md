# CSI5370-HPMS
Project for CSI5370 Software Verification and Testing


# Creating Database image and Start it
Start from root of project
run <docker build -t csi5370-final-db .> command from command line (do not inclue <>)
open docker and make sure there are no containers with name 'csi5370-final-db'
run <docker run -d --name csi5370-final-db -p 5432:5432 csi5370-final-db> from command line (do not include <>)

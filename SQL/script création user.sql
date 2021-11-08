CREATE USER 'isa'@'localhost' 
IDENTIFIED BY 'didii';

CREATE USER 'mery'@'localhost' 
IDENTIFIED BY 'meme';

CREATE USER 'professeur'@'localhost' 
IDENTIFIED BY 'premier';

GRANT ALL
ON *
TO 'isa'@'localhost'
with grant option;

GRANT ALL
ON *
TO 'mery'@'localhost'
with grant option;

GRANT SELECT, UPDATE, DELETE, INSERT
ON *
TO 'professeur'@'localhost';
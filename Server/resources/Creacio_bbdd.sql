#CREACIO DE LA BASE DE DADES
DROP DATABASE IF EXISTS AgeRoyale;
CREATE DATABASE AgeRoyale;
USE AgeRoyale;

#DESTRUCCIO DE LES TAULES
DROP TABLE IF EXISTS partida CASCADE;
DROP TABLE IF EXISTS UsuariTropa CASCADE;
DROP TABLE IF EXISTS tropa CASCADE;
DROP TABLE IF EXISTS amic CASCADE;
DROP TABLE IF EXISTS usuari CASCADE;
DROP TABLE IF EXISTS stats CASCADE;

#CREACIO DE LES TAULES
CREATE TABLE stats(
    idStat          int not null auto_increment,
    totalPartides   int,
    totalVictories  int,
    winrate         float,
    avgDurationVictories float,
    PRIMARY KEY (idStat)
);

CREATE TABLE usuari(
    idUser      int not null auto_increment,
    nickname    VARCHAR(255),
    email       VARCHAR(255),
    password    VARCHAR(255),
    idStats     int,
    isOnline    boolean,
    PRIMARY KEY (idUser),
    FOREIGN KEY (idStats) REFERENCES stats (idStat)
);

CREATE TABLE amic(
  id_u1     int not null,
  id_u2     int not null,
  PRIMARY KEY (id_u1, id_u2),
  FOREIGN KEY (id_u1) REFERENCES usuari(idUser),
  FOREIGN KEY (id_u2) REFERENCES usuari(idUser)
);

CREATE TABLE tropa(
  idTropa   int not null auto_increment,
  atac      int,
  vida      int,
  cost      int,
  tipus     boolean,
  PRIMARY KEY (idTropa)
);

CREATE TABLE UsuariTropa(
    idUser  int not null,
    idTropa int not null,
    FOREIGN KEY (idUser) REFERENCES usuari(idUser),
    FOREIGN KEY (idTropa)REFERENCES tropa(idTropa)
);

CREATE TABLE partida(
    idPartida   int not null auto_increment,
    duration    int,
    publica 	boolean,
    name		varchar(255),
    host 		varchar(255),
    date        varchar(255),
    player1     int,
    player2     int,
    PRIMARY KEY (idPartida),
    FOREIGN KEY (player1) REFERENCES usuari(idUser),
    FOREIGN KEY (player2) REFERENCES usuari(idUser)
);

SELECT* FROM usuari;

#Importacio basica de informació
INSERT INTO stats(TOTALPARTIDES, TOTALVICTORIES, WINRATE, AVGDURATIONVICTORIES) VALUES
    (10,  4, 30.2, 200), (15, 10, 70, 150), (28, 35, 65, 200), (25, 10, 40, 100), (248, 234, 21, 10);

INSERT INTO usuari (nickname, email, password, idStats, isOnline) values
    ('VXGamez', 'victor.xirau@students.salle.url.edu', '1234', 1, false),
    ('Lidia', 'lidia.figueras@students.salle.url.edu', '1234', 2, false),
    ('Adria', 'adria.pajares@students.salle.url.edu', '1234', 3, false),
    ('Marthin', 'marti.ejarque@students.salle.url.edu', '1234', 4, false),
    ('Bernat', 'bernat.segura@students.salle.url.edu', '1234', 5, false);

INSERT INTO partida (duration, publica, name, host, date, player1, player2) VALUES
    (200, true, 'Prova1', 'admin', '20-05-2020', 1, 2), (150, true, 'Prova2', 'admin', '19-05-2020', 3, 4), (478, false, 'Prova3', 'admin',  '18-05-2020', 5, 1);

INSERT INTO amic (id_u1, id_u2) VALUES
    (1, 2), (2, 3), (3, 4), (4, 5), (5, 1);

INSERT INTO tropa (ATAC, VIDA, COST, TIPUS) values
    (30, 100, 200, true), (10, 300, 100, false), (50, 50, 100, true), (40, 70, 50, true), (10, 150, 2, false);

INSERT INTO UsuariTropa (idUser, idTropa) VALUES
    (1, 1), (1, 2),
    (2, 2), (2, 3),
    (3, 3), (3, 4),
    (4, 4), (4, 5),
    (5, 5), (5, 1);

SELECT * FROM partida;

###########################################     AMIC    ###############################################################################################
#get Amics
SELECT us.* FROM usuari as us, amic as am WHERE (us.idUser = am.id_u1 and  1 = am.id_u2) OR (us.idUser = am.id_u2 and  1 = am.id_u1);

#set Amic
INSERT INTO amic (id_u1, id_u2) VALUE (1, 4);

#delete amic
DELETE FROM AgeRoyale.amic WHERE ( 1 = id_u1 and  4 = id_u2) OR (4 = id_u1 and  1 = id_u2);

###########################################     STATS    ###############################################################################################

#get Stats from id user
SELECT st.* FROM stats as st, usuari as us WHERE us.idUser = 1 and us.idStats = st.idStat;

#get Stats from id stat
SELECT st.* FROM stats AS st where idStat = 1;

#add Stat
INSERT INTO AgeRoyale.stats (idStat, totalPartides, totalVictories, winrate, avgDurationVictories) VALUE (6, 1, 1, 1, 1);

#update stat
UPDATE AgeRoyale.stats SET AgeRoyale.stats.totalPartides = 31, AgeRoyale.stats.totalVictories = 101, AgeRoyale.stats.winrate = 201, AgeRoyale.stats.avgDurationVictories = 29 WHERE AgeRoyale.stats.idStat = 1;

#get max pk value from stats
SELECT st.idStat FROM AgeRoyale.stats as st ORDER BY st.idStat DESC LIMIT 1;

#remove stats
DELETE FROM AgeRoyale.stats WHERE idStat = 6;
DELETE FROM AgeRoyale.stats where AgeRoyale.usuari.idUser = 1 AND AgeRoyale.usuari.idStats = AgeRoyale.stats.idStat;

#reset stats
UPDATE AgeRoyale.stats SET AgeRoyale.stats.totalPartides = 0, AgeRoyale.stats.totalVictories = 0, AgeRoyale.stats.winrate = 0, AgeRoyale.stats.avgDurationVictories = 0 WHERE AgeRoyale.stats.idStat = 6;

###########################################     TORPES    ###############################################################################################

#get tropes from user id
SELECT tr.* FROM AgeRoyale.tropa as tr, AgeRoyale.usuaritropa as ustr WHERE ustr.idUser = 1 and ustr.idTropa = tr.idTropa;

#get all tropes
SELECT tropa.* FROM AgeRoyale.tropa;

#update Tropa
UPDATE AgeRoyale.tropa SET AgeRoyale.tropa.atac = 31, AgeRoyale.tropa.vida = 101, AgeRoyale.tropa.cost = 201, AgeRoyale.tropa.tipus = false WHERE AgeRoyale.tropa.idTropa = 1;

#add tropa
INSERT INTO AgeRoyale.tropa (atac, vida, cost, tipus) VALUE (1, 1, 1, 1);

#remove
DELETE FROM AgeRoyale.tropa WHERE idTropa = 1;

###########################################     USUARI    ###############################################################################################
#get all users
SELECT us.* FROM AgeRoyale.usuari as us;

#get user from id
SELECT us.* FROM AgeRoyale.usuari as us WHERE idUser = 1;

#add user
INSERT INTO AgeRoyale.usuari (nickname, email, password, idStats) VALUE (1, 1, 1, 1);

#update
UPDATE AgeRoyale.usuari SET nickname = '' and email ='' and password = '' and idStats = 0 WHERE idUser = 1;

#next pk
SELECT us.idUser FROM AgeRoyale.usuari as us ORDER BY us.idUser DESC LIMIT 1;

#remove
DELETE FROM AgeRoyale.usuari WHERE idUser = 1;

#exists login
SELECT if(COUNT(*) = 1, us.idUser, -1) as exist FROM AgeRoyale.usuari AS us WHERE us.nickname = 'VXGamez' AND us.password = 'contrasenya';

#exist registre
SELECT if(COUNT(*) = 1, 1, -1) as exist FROM AgeRoyale.usuari AS us WHERE us.nickname = 'VGamez' OR us.email = 'victor.xirau@students.salle.url.edu';

#exits al canvi de nom / email
SELECT if(COUNT(*) > 1, 1, -1) as exist FROM AgeRoyale.usuari AS us WHERE us.nickname = 'bernat' OR us.email = 'victor.xirau@students.salle.url.edu';
SELECT us.* FROM AgeRoyale.usuari AS us WHERE us.nickname = 'bernat' OR us.email = 'victor.xirau@students.salle.url.edu';

#
UPDATE AgeRoyale.usuari SET isOnline = true WHERE idUser = 5;

###########################################     PARTIDA    ###############################################################################################
#get partida from id
SELECT par.* FROM AgeRoyale.partida AS par WHERE idPartida = 1;

#get partida from user
SELECT par.* FROM AgeRoyale.partida AS par WHERE player1 = 1 OR player2 = 1;

#get all partides
SELECT par.* FROM AgeRoyale.partida AS par;

#add partida
INSERT INTO AgeRoyale.partida (idPartida, duration, date, player1, player2) VALUE ();

#delete partida
DELETE FROM AgeRoyale.partida WHERE idPartida = 1;

#delete partida on user remove
DELETE FROM AgeRoyale.partida WHERE player1 = 1 OR player2 = 1;

#gestio de pk
SELECT par.idPartida FROM AgeRoyale.partida as par ORDER BY par.idPartida DESC LIMIT 1;

###########################################     USUARI - TROPA  #############################################################################################
#on remove tropa
DELETE FROM AgeRoyale.usuaritropa WHERE idTropa = 1;

#on remove usuari
DELETE FROM AgeRoyale.usuaritropa WHERE idUser = 1;

#add tropa to user
INSERT INTO AgeRoyale.UsuariTropa (idUser, idTropa) VALUES (1, 1);

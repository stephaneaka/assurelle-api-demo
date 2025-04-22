INSERT INTO USER_ACCOUNT (EMAIL, PASSWORD,NAME,ROLES,ENABLED) 
VALUES 
('admin@devolution.com', 'password', 'Administrateur', 'ROLE_USER,ROLE_ADMIN',1),
('user1@devolution.com', 'password', 'Ophelie AMANOUA', 'ROLE_USER',1),
('user2@devolution.com', 'password', 'Vicoire Lou Epse CISSOKO', 'ROLE_USER',1),
('user3@devolution.com', 'password', 'Andrea FLAVIE YAPO 3', 'ROLE_USER',1);

INSERT INTO VEHICLE_CATEGORY (ID, NAME, DESCRIPTION) 
VALUES 
(201,'Vehicle personnel', 'Promenade et affairel'), 
(202,'Motocycle/tricycle', 'Véhicules motorisés à 2 ou 3 roues'), 
(203,'Transport public', 'Véhicule transport de personnes'), 
(204,'Taxi', 'Véhicule de transport avec taximètres');


INSERT INTO GUARANTEE (IS_PRIMARY,NAME, DESCRIPTION, RATE_VALUE) 
VALUES 
(1,'RC','Responsabilité civile',0), 
(0,'DOMMAGES','Garantie dommages',2.60), 
(0,'COLLISION','Garantie tierce collision', 1.65), 
(0,'PLAFONNEE','Garntie tiere plafonnée', 4.20), 
(0,'VOL','Garantie vol', 0.14), 
(0,'INCENDIE','Garantie incendie', 0.15);

INSERT INTO PRODUCT (NAME, DESCRIPTION) 
VALUES 
('Papillon','RC, DOMMAGE, VOL'), 
('Douby','RC, DOMMAGE, COLLISION'), 
('Douyou','RC, DOMMAGE,COLLISION, INCENDIE'),  
('Toutourisque','TOUTES GARANTIES');

INSERT INTO PRODUCT_VEHICLE_CATEGORIES (PRODUCT_ID, VEHICLE_CATEGORIES_ID) 
VALUES 
(1,201), 
(2,202),
(3,201),
(3,202),
(4,201);

INSERT INTO PRODUCT_GUARANTEES (PRODUCT_ID, GUARANTEES_ID) 
VALUES 
(1,1), 
(1,2), 
(1,5), 
(2,1), 
(2,2), 
(2,3), 
(3,1), 
(3,2), 
(3,3), 
(3,6),
(4,1),
(4,2),
(4,3),
(4,4),
(4,5),
(4,6);

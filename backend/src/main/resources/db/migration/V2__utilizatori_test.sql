-- Utilizatori de test. Hash-uri BCrypt luate din Laboratorul 4:
--   profesor@teste.ro -> parola admin1234
--   student@teste.ro  -> parola client1234
INSERT INTO utilizator (nume, email, parola, rol) VALUES
    ('Profesor Test', 'profesor@teste.ro',
     '{bcrypt}$2a$10$kI0keYqMoei6MWR6Fxk8uurWc0aHFp6MVaeXlMKw8SE5bG/JF199O',
     'PROFESOR'),
    ('Student Test', 'student@teste.ro',
     '{bcrypt}$2a$10$H9awdA2YaR7JrA2tSJYpGOdFnxDcQC0GRoMLH3oNhqCgKYUiXOpNm',
     'STUDENT');

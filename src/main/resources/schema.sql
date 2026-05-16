CREATE TABLE estudiante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    apellido VARCHAR(255),
    correo VARCHAR(255) UNIQUE
);

CREATE TABLE materia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) UNIQUE,
    creditos INT
);

CREATE TABLE nota (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    valor DOUBLE,
    porcentaje DOUBLE,
    observacion VARCHAR(255),
    estudiante_id BIGINT,
    materia_id BIGINT
);
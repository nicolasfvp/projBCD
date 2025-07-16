
DROP TABLE IF EXISTS desafio_concluido_feito;
DROP TABLE IF EXISTS desafio_concluidos;
DROP TABLE IF EXISTS noites_acampados;
DROP TABLE IF EXISTS acampamentos;
DROP TABLE IF EXISTS desafio_de_especialidade_feita;
DROP TABLE IF EXISTS desafio_feitos;
DROP TABLE IF EXISTS desafios;
DROP TABLE IF EXISTS insignias;
DROP TABLE IF EXISTS especialidades;
DROP TABLE IF EXISTS areas_de_conhecimento;
DROP TABLE IF EXISTS saude_dados;
DROP TABLE IF EXISTS problemas_saude;
DROP TABLE IF EXISTS vinculos;
DROP TABLE IF EXISTS responsaveis;
DROP TABLE IF EXISTS pessoas;
DROP TABLE IF EXISTS tipos_sanguineos;

//
CREATE TABLE IF NOT EXISTS tipos_sanguineos (
                                                id_tipo_sanguineo BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                tipo VARCHAR(5) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS pessoas (
                                       id_pessoa BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(255),
    data_nascimento DATE,
    id_tipo_sanguineo BIGINT,
    FOREIGN KEY (id_tipo_sanguineo) REFERENCES tipos_sanguineos(id_tipo_sanguineo)
    );

CREATE TABLE IF NOT EXISTS responsaveis (
                                            id_responsavel BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS vinculos (
                                        id_vinculo BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        id_pessoa BIGINT NOT NULL,
                                        id_responsavel BIGINT NOT NULL,
                                        FOREIGN KEY (id_pessoa) REFERENCES pessoas(id_pessoa),
    FOREIGN KEY (id_responsavel) REFERENCES responsaveis(id_responsavel)
    );

CREATE TABLE IF NOT EXISTS problemas_saude (
                                               id_problema_saude BIGINT AUTO_INCREMENT PRIMARY KEY,
                                               nome_problema VARCHAR(255) NOT NULL,
    descricao TEXT
    );

CREATE TABLE IF NOT EXISTS saude_dados (
                                           id_saude_dado BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           id_pessoa BIGINT NOT NULL,
                                           id_problema_saude BIGINT NOT NULL,
                                           FOREIGN KEY (id_pessoa) REFERENCES pessoas(id_pessoa),
    FOREIGN KEY (id_problema_saude) REFERENCES problemas_saude(id_problema_saude)
    );

CREATE TABLE IF NOT EXISTS areas_de_conhecimento (
                                                     id_area_de_conhecimento BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                     nome VARCHAR(255) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS especialidades (
                                              id_especialidade BIGINT AUTO_INCREMENT PRIMARY KEY,
                                              nome VARCHAR(255) NOT NULL,
    id_area_de_conhecimento BIGINT NOT NULL,
    FOREIGN KEY (id_area_de_conhecimento) REFERENCES areas_de_conhecimento(id_area_de_conhecimento)
    );

CREATE TABLE IF NOT EXISTS insignias (
                                         id_insignia BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         nome VARCHAR(255) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS desafios (
                                        id_desafio BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        nome VARCHAR(255) NOT NULL,
    id_insignia BIGINT,
    FOREIGN KEY (id_insignia) REFERENCES insignias(id_insignia)
    );

CREATE TABLE IF NOT EXISTS desafio_feitos (
                                              id_desafio_feito BIGINT AUTO_INCREMENT PRIMARY KEY,
                                              id_desafio BIGINT NOT NULL,
                                              id_pessoa BIGINT NOT NULL,
                                              data DATE NOT NULL,
                                              FOREIGN KEY (id_desafio) REFERENCES desafios(id_desafio),
    FOREIGN KEY (id_pessoa) REFERENCES pessoas(id_pessoa)
    );

CREATE TABLE IF NOT EXISTS desafio_de_especialidade_feita (
                                                              id_desafio_de_especialidade_feita BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                              id_desafio BIGINT NOT NULL,
                                                              id_pessoa BIGINT NOT NULL,
                                                              id_especialidade BIGINT NOT NULL,
                                                              data DATE NOT NULL,
                                                              FOREIGN KEY (id_desafio) REFERENCES desafios(id_desafio),
    FOREIGN KEY (id_pessoa) REFERENCES pessoas(id_pessoa),
    FOREIGN KEY (id_especialidade) REFERENCES especialidades(id_especialidade)
    );

CREATE TABLE IF NOT EXISTS acampamentos (
                                            id_acampamento BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            nome VARCHAR(255) NOT NULL,
    data DATE NOT NULL
    );

CREATE TABLE IF NOT EXISTS noites_acampados (
                                                id_noite_acampado BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                id_acampamento BIGINT NOT NULL,
                                                id_pessoa BIGINT NOT NULL,
                                                FOREIGN KEY (id_acampamento) REFERENCES acampamentos(id_acampamento),
    FOREIGN KEY (id_pessoa) REFERENCES pessoas(id_pessoa)
    );

CREATE TABLE IF NOT EXISTS desafio_concluidos (
                                                  id_desafio_concluido BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                  descricao TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS desafio_concluido_feito (
                                                       id_desafio_concluido_feito BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                       id_desafio_concluido BIGINT NOT NULL,
                                                       id_pessoa BIGINT NOT NULL,
                                                       data_inicio DATE,
                                                       data_fim DATE,
                                                       FOREIGN KEY (id_desafio_concluido) REFERENCES desafio_concluidos(id_desafio_concluido),
    FOREIGN KEY (id_pessoa) REFERENCES pessoas(id_pessoa)
    );


INSERT INTO tipos_sanguineos (tipo) VALUES ('A+'), ('A-'), ('B+'), ('B-'), ('AB+'), ('AB-'), ('O+'), ('O-');
INSERT INTO areas_de_conhecimento (nome) VALUES ('Ciência e Tecnologia'), ('Cultura'), ('Desportos'), ('Habilidades Escoteiras'), ('Serviços');
INSERT INTO insignias (nome) VALUES ('Aprender'), ('Cruzeiro do Sul'), ('Lobo Caçador'); -- Adicionado Lobo Caçador para teste do Cruzeiro do Sul


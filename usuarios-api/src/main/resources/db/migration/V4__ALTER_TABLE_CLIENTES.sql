ALTER TABLE tb_clientes 
ADD COLUMN senha VARCHAR(255) NOT NULL,
ADD COLUMN administrador TINYINT NOT NULL,
ADD COLUMN usuario_externo TINYINT NOT NULL;

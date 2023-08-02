/**
* Sistema para gestão de OS
* @author Eduardo Casagrande de Sá
*/

create database dbsistema;
use dbsistema;
show tables;

-- unique (não permite valores duplicados no campo)
create table usuarios(
	id int primary key auto_increment,
	nome varchar(50) not null,
    login varchar(15) not null unique,
    senha varchar(250) not null,
    perfil varchar(10) not null
);

drop table usuarios;

describe usuarios;

select * from usuarios;

-- uso do md5() para criptografar uma senha
insert into usuarios (nome,login,senha,perfil) values ('Sander Hideki','hidekibtw', md5('123@senac'),'bugg-head');
insert into usuarios (nome,login,senha) values ('Welintum Grande','wegrande', md5('grande123'));
insert into usuarios (nome,login,senha,perfil) values ('Wudson Escada','Wudsonlutador', md5('grande123'),'wudson');
insert into usuarios (nome,login,senha,perfil) values ('Admistrador','admin', md5('admin'),'admin');

select * from usuarios order by nome;
select * from usuarios order by nome desc;

select * from usuarios where nome = "Admistrador";
select * from usuarios where login = "admin";


-- CRUD READ
select nome from usuarios;
select nome,login from usuarios;
select * from usuarios where nome like 'al%';
select * from usuarios where nome = 'Sander Hideki';
select * from usuarios where id = 1;
select * from usuarios where login = 1;

-- Login(autenticação)
select * from usuarios where login = 'admin' and senha = md5('admin');

select nome as Contato, login as Login, senha as senha from usuarios order by nome;
 -- CRUD UPDATE
 update usuarios set nome = 'Welintum Grande', login = 'wegrande', senha = 'grande123' where id = 2;

-- CRUD Delete
-- CUIDADO !!! (Sempre usar a cláusula where junto com a chave primária)
delete from usuarios where id = 2;

-- busca avançada pelo nomne (estilo google)
select nome from usuarios where nome like 'wa%' order by nome;
--------------------------------------------------------------------------------------------------------------

drop table cliente;

create table cliente (
idcli int primary key auto_increment,
nome varchar (50) not null,
fone varchar(18) not null ,
email varchar (50) not null,
cep varchar(10),
endereco varchar(50) not null,
numero varchar (10) not null,
comp varchar (20),
bairro varchar (30) not null,
cidade varchar (30) not null,
uf char(2) not null,
ref varchar (50)
);

describe cliente;

select * from cliente;

insert into cliente (nome,fone,email,cep,endereco,numero,comp,bairro,cidade,uf,ref) values ('Edilson','11945314955','hidekiteramachi@hotmail.com', 'Rua Adolfo Asson','Nenhuma','Nenhuma');

select * from cliente order by nome desc;
select * from cliente order by nome desc;
delete from cliente where idcli = 1;

-- CRUD READ
select nome from cliente;
select nome,telefone from cliente;
select * from cliente where nome like 'al%';
select * from cliente where nome = 'Sander Hideki';
select * from cliente where id = 1;
select * from cliente where nome = 1;


select nome as Contato, fone as Telefone, email as email, endereco as Endereço, comp as Complemento from cliente order by nome;
 -- CRUD UPDATE
 update cliente set nome = 'Waltinho Grande', fone = '11945684122', email = 'welintumgrande@gmail.com', endereco = 'rua pasteour, 58', comp = 'Nenhuma' where id = 5;

-- CRUD Delete
-- CUIDADO !!! (Sempre usar a cláusula where junto com a chave primária)
delete from cliente where id = 2;


-- Alterações na estrutura da tabela
-- adicionar uma coluna a tabela
alter table cliente add column obs varchar (100);
-- adicionar uma coluna após um local específico
alter table cliente add column fone2 varchar(15) not null after fone;

-- alterando tipo de dados ou validações
-- ATENÇÃO aos dados já cadastrados!
alter table cliente modify fone2 varchar (20);

drop table cliente;

drop table servicos;

/* Relacionamento de tabelas 1 - N */

-- timestamp default current_timestamp (data e hora automática)
-- decimal (números não inteiros) 10,2 (dígitos, casas decimais)
-- foreign key (FK) chave estrangeira
-- 1 (FK) --------- N (PK)
create table servicos (
	os int primary key auto_increment,
    dataOS timestamp default current_timestamp,
    servico varchar(200) not null,
    valor decimal(10,2), 
    idcli int not null,
    foreign key (idcli) references cliente(idcli)
    );
    
    insert into servicos (servico,valor, idCli) values ('troca de pasta termica',500,1);
    
    select * from produtos;
    
    -- selecionando o conteúdo de 2 ou mais tabelas
    select * from servicos
    inner join cliente on servicos.idcli = cliente.idcli;
    
    use dbsistema;
    
    /** RELATÓRIOS **/
    select nome,fone,email from cliente order by nome;
    
    -- Serviços
    select servicos.os,servicos.dataOS,servicos.servico,servicos.servico,servicos.valor,cliente.nome from servicos inner join cliente on servicos.idcli= cliente.idcli;
    
    
    
    
    
    --------------------------------
    
    CREATE DATABASE produtosdb default character set = 'utf8mb4';

use produtosdb;

create table produtos(
	idproduto int auto_increment primary key,
    nomeproduto varchar(100),
    descricao text,
    categoria varchar(50),
    lote varchar(20),
    datafabricacao varchar(20),
    datavalidade varchar(20),
    preco decimal(10,2),
    imagemproduto varchar(200)
);
drop database produtosdb;
----------------------------------------------------------


create table fornecedores (
codefornecedor int primary key auto_increment,
razaosocial varchar (50) not null,
fone varchar(18) not null ,
cnpj varchar (20) not null,
email varchar (50) not null,
cep varchar(10),
endereco varchar(50) not null,
numero varchar (20) not null,
comp varchar (20),
bairro varchar (30) not null,
cidade varchar(30) not null,
uf char(2) not null,
ref varchar (50)
);
drop table fornecedores;
select * from fornecedores;

create table produtos (
codeproduto int primary key auto_increment,
barcode varchar(20) not null,
descricao varchar(50) not null,
foto longblob null,
estoque int not null,
estoquemin int not null,
valor varchar(15) not null,
unidademedida char(2) not null,
localarmazenagem varchar (50) not null,
idfornecedor int null, 
foreign key (idfornecedor) references fornecedores(codefornecedor)
);
select * from produtos;

drop table produtos;	
-- CRUD Delete
-- CUIDADO !!! (Sempre usar a cláusula where junto com a chave primária)
delete from produtos where id = 2;

update produtos set barcode="KD92318929121",descricao="Prancheta daorao",estoque=9,estoquemin=5,valor=11,unidademedida="UN",localarmazenagem="Minas Gerais" where codeproduto =1;

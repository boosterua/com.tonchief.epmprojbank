/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     05/20/2017 01:27:20                          */
/*==============================================================*/


drop table if exists Accounts;

drop table if exists Cards;

drop table if exists Clients;

drop table if exists Fees;

drop table if exists Transactions;

/*==============================================================*/
/* Table: Accounts                                              */
/*==============================================================*/
create table Accounts
(
   id_account           int not null auto_increment,
   number               bigint,
   id_client            int,
   id_card              int,
   primary key (id_account)
);

/*==============================================================*/
/* Table: Cards                                                 */
/*==============================================================*/
create table Cards
(
   id_card              int not null auto_increment,
   id_fee               int,
   number               numeric(16),
   exp_date             date,
   primary key (id_card)
);

/*==============================================================*/
/* Table: Clients                                               */
/*==============================================================*/
create table Clients
(
   id_client            int not null auto_increment,
   name                 varchar(42),
   email                varchar(50),
   password             varchar(32),
   role                 bigint,
   primary key (id_client)
);

/*==============================================================*/
/* Table: Fees                                                  */
/*==============================================================*/
create table Fees
(
   id_fee               int not null auto_increment,
   name                 varchar(20),
   trans_fee            double,
   newcard_fee          double,
   apr                  double,
   primary key (id_fee)
);

/*==============================================================*/
/* Table: Transactions                                          */
/*==============================================================*/
create table Transactions
(
   id_trans             int not null auto_increment,
   id_account           int ,
   cr_account           bigint,
   amount               int,
   date                 date,
   primary key (id_trans)
);

alter table Accounts add constraint FK_Reference_2 foreign key (id_client)
      references Clients (id_client) on delete restrict on update restrict;

alter table Accounts add constraint FK_Reference_3 foreign key (id_card)
      references Cards (id_card) on delete restrict on update restrict;

alter table Accounts add constraint FK_Reference_4 foreign key (id_card)
      references Cards (id_card) on delete restrict on update restrict;

alter table Cards add constraint FK_Reference_6 foreign key (id_fee)
      references Fees (id_fee) on delete restrict on update restrict;

alter table Transactions add constraint FK_Reference_5 foreign key (id_account)
      references Accounts (id_account) on delete restrict on update restrict;


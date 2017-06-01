DROP TABLE IF EXISTS accounts ; 
CREATE TABLE accounts
(
	id_account int not null auto_increment
		primary key,
	number mediumtext null,
	is_blocked tinyint(1) default '0' null,
	client_id int not null,
	constraint account_id_uindex
		unique (id_account)
)
;

create index fk_accounts_clients1_idx
	on accounts (client_id)
;

DROP TABLE IF EXISTS cards ; 
CREATE TABLE cards
(
	id_card int not null auto_increment,
	number bigint null,
	exp_date date null,
	fee_id int not null,
	account_id int not null,
	primary key (id_card, fee_id, account_id),
	constraint fk_cards_accounts1
		foreign key (account_id) references epmprojbank.accounts (id_account)
)
;

create index fk_cards_accounts1_idx
	on cards (account_id)
;

create index fk_cards_fees1_idx
	on cards (fee_id)
;

DROP TABLE IF EXISTS clients ; 
CREATE TABLE clients
(
	id_client int not null auto_increment primary KEY,
	name varchar(42) null,
	email varchar(50) null,
	password varchar(32) null,
	role int null
)
;

alter table accounts
	add constraint fk_accounts_clients1
		foreign key (client_id) references epmprojbank.clients (id_client)
;

DROP TABLE IF EXISTS fees ; 
CREATE TABLE fees
(
	id_fee int not null auto_increment  primary key,
	name varchar(20) null,
	trans_fee double null,
	newcard_fee double null,
	apr double null
)
;

alter table cards
	add constraint fk_cards_fees1
		foreign key (fee_id) references epmprojbank.fees (id_fee)
;

DROP TABLE IF EXISTS transactions ; 
CREATE TABLE transactions
(
	id_tr int not null auto_increment,
	cr_account bigint null,
	amount decimal(9,2) null,
	date date null,
	account_id int not null,
	description varchar(128) null,
	primary key (id_tr, account_id),
	constraint fk_transactions_accounts1
		foreign key (account_id) references epmprojbank.accounts (id_account)
)
;

create index fk_transactions_accounts1_idx
	on transactions (account_id)
;


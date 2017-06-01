# com.tonchief.epmprojbank
/* Created by ton chief. */


###*ТРЕБОВАНИЯ К ВЫПОЛНЕНИЮ ПРОЕКТА*

1. Информацию о предметной области хранить в БД (рекомендуется MySQL), для доступа использовать JDBC.
2. На основе сущностей предметной области создать классы их описывающие.
3. При реализации алгоритмов бизнес-логики использовать шаблоны GoF (Factory Method, Command, Singleton, Builder, Strategy) и Model-View-Controller.
4. Используя сервлеты и JSP, реализовать функциональности, предложенные в постановке конкретной задачи.
5. При разработке JSP использовать собственные теги.
6. При разработке бизнес логики использовать сессии и фильтры.
7. Приложение должно поддерживать работу с кириллицей, в том числе и при хранении информации в БД.
8. Классы и методы должны иметь отражающую их функциональность названия и должны быть грамотно структурированы по пакетам.
9. Оформление кода должно соответствовать Java Code Convention.
10. При разработке использовать журналирование событий (Log4j).
11. Код должен содержать комментарии хотя бы частично.

> *from another ver. of requirements*
> * Информацию о предметной области хранить в БД, для доступа использовать API JDBC с использованием пула соединений, стандартного или разработанного самостоятельно. В качестве СУБД рекомендуется MySQL или Derby.
>*	В страницах JSP применять библиотеку JSTL и разработать собственные теги.

##Задача
### 2.	Система **Платежи**. ###
Клиент имеет одну или несколько **Кредитных Карт**, каждая из которых соответствует некоторому **Счету** в системе платежей.
**Клиент** может при помощи **Счета** сделать **Платеж**, заблокировать **Счет** и пополнить **Счет**.
**Администратор** снимает блокировку.



:+1:

## Development ##
-------------------
 *Data Structure & Services:*
```    
     Client-> Cards[] <-Accounts[]
     
     Client
     -> makePayment(details: from, to, amount, descr);
     -> replenishAccount(acctId, amount, source[anotherAccount|cash]);
     -> blockAccount(acctId);
     -> register(); // fill out form including client id fields as well as type of card requested.
     
     Administrator
     +> blockAccount();   //+ListAccounts
     +> removeAccountBlock(); 
     +> issueNewCard(); // List Fresh Users 
     -> listClientsByCardType(VisaClassic); list all clients with Visa Classic Cards
     -> listClientsWithBlockedAccounts();
     -> approveClient(); // incl.issue new account and card.
     -> listCardsOfType;
     

```
*DB:*
```    
          Client [name, id, cardId, role:adm|usr]
          Cards [id, nr, refAcctId, expDate]
          Accounts [id, nr, clientId, isActive]
          Payments [id, dtAcct, crAcct, amt, date]
```


Service.Client: 
 - make payment: choose account*, choose action [block,replenish,makepayment]. *select accounts

 



To be continued...


Stack of tech
Java-Core:Threads, JDBC (PS+Transactions), JSP, JSTL, FrontEnd:Bootstrap,JavaScript,CSS
Multi-threading: new user> customer || new acct
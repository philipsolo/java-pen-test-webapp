create table userAccounts
(
    Firstname      varchar(50)  not null,
    Lastname       varchar(50)  not null,
    Email          varchar(50)  not null,
    Phone          varchar(50)  not null,
    Username       varchar(50)  not null,
    Pwd            varchar(200) not null,
    salt           varchar(200) not null,
    account_type   varchar(200) not null,
    Login_attempts varchar(200) null,
    primary key (Username, Pwd)
);

create table winning_number
(
    lottery_win varchar(200) not null
);

insert into winning_number (lottery_win) VALUE ('1,2,3,4,5,6');


#Guest user, password is Philipsolo1
INSERT INTO userAccounts VALUES
('philip','solo','philipsolo4@gmail.com','44-0191-5678901','philipsolo','02a64fffa3569941e7f8dde8baced3a243670e32c656fadb996a16a77638e09b331b7911726dadf8ba2e71a5afc31cf7840e6561f8ee04a6057550ee1e237c53','aab776e656a4d0599baed7ddfe7c9705','public','0');


#Admin, password is Johnsolo1
INSERT INTO userAccounts VALUES
('john','solo','johnsolo@gmail.com','44-0191-5678901','johnsolo','4843ee05b4e486bc7ce3864f8e13eb80e46c5de1d0580f18fb39465e69cc0cec2cc0693215a961f434e6ae39291ea786ec24c1c61ef8e411615449df72755068','ac97dcbed4aa742911e6e56667bcc889','admin','0');



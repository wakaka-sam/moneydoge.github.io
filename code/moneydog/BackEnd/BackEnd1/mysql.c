use moneydog;
create table user(
    openid varchar(30),
    nikname varchar(20),
    avatarUrl text,
    gender  varchar(1),
    school  varchar(30),
    wechat  varchar(50),
    phoneNum varchar(12),
    state  int,
    registe_time	datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    primary key (openid)
);

create table expressage(
	
	pid int AUTO_INCREMENT,
	
	uid1 varchar(32) NOT NULL,
	
	uid2 varchar(32),
	
	express_loc varchar(100),
	
	arrive_time datetime,
	
	loc varchar(100),
	
	phone varchar(12),
	
	wechat varchar(32),
	
	num  int,
	
	pay   int,
	
	state int,
	
	remark	text,
	
	issue_time	datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	primary key (pid),
	foreign key (uid1) references user(openid),
	foreign key (uid2) references user(openid)
);

create table for_help(
	
	fid int AUTO_INCREMENT ,
	
	uid1 varchar(32) NOT NULL,
	
	uid2 varchar(32),
	
	title varchar(100),
	
	content text,

	phone varchar(12),
	
	wechat varchar(32),
	
	ending_time datetime,
	
	pay   int,
	
	state int,
	
	issue_time	datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	primary key (fid),
	foreign key (uid1) references user(openid),
	foreign key (uid2) references user(openid)
);
create table errand(
	
	rid int AUTO_INCREMENT ,
	
	uid1 varchar(32) NOT NULL,
	
	uid2 varchar(32),
	
	title varchar(100),

	content text,
	
	phone varchar(12),
	
	wechat varchar(32),
	
	ending_time datetime,
	
	pay   int,
	
	state int,
	
	issue_time	datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	primary key (rid),
	foreign key (uid1) references user(openid),
	foreign key (uid2) references user(openid)
);
create table second_hand(
	
	sid int AUTO_INCREMENT ,
	
	uid1 varchar(32) NOT NULL,
	
	uid2 varchar(32),
	
	object_name varchar(100),

	content text,
	
	phone varchar(12),
	
	wechat varchar(32),
	
	ending_time datetime,
	
	photo_url text,
	
	pay   int,
	
	state int,
	
	issue_time	datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	primary key (sid),
	foreign key (uid1) references user(openid),
	foreign key (uid2) references user(openid)
);
create table questionair(
    qid int AUTO_INCREMENT,
    uid varchar(32),
    pay int,
    name text,
    description text,
    content text,
    content_count text,
    num int,
    state int ,    
    issue_time  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    primary key(qid),
    foreign key(uid) references user(openid)
);
create table questionairNote(
	uid varchar(32),
	qid int,
	issue_time  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	primary key(uid,qid),
	foreign key(uid) references user(openid),
	foreign key(qid) references questionair(qid)
)
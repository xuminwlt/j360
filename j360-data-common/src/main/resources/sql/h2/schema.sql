    drop table if exists ss_user_role;

    drop table if exists ss_role;

    drop table if exists ss_user;
    
    drop table if exists ss_team;

    create table ss_role (
        id bigint  ,
    	name varchar(255) ,
    	permissions varchar(255),
        primary key (id)
    );

    create table ss_user (
       	id bigint  ,
        login_name varchar(255) ,
        name varchar(64),
        password varchar(255),
        salt varchar(64),
        email varchar(128),
        status varchar(32),
        team_id bigint,
        primary key (id)
    );

    create table ss_user_role (
        user_id bigint ,
        role_id bigint ,
        primary key (user_id, role_id)
    );
    
   	create table ss_team (
       	id bigint  ,
    	name varchar(255)  ,
    	master_id bigint,
        primary key (id)
    );
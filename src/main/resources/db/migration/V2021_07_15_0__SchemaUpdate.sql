CREATE TABLE IF NOT EXISTS `file_token`
(
    `token`         varchar(255) not null,
    `creation_date` datetime,
    `file_name`     varchar(255),
    `file_path`     varchar(255),
    primary key (token)
);

create table if not exists tools
(
    ID                 bigint auto_increment
        primary key,
    name               varchar(100) not null,
    description        text         null,
    Title              varchar(255) not null,
    seo_keyword        varchar(255) null,
    seo_description    text         null,
    seo_canonical_url  varchar(255) null,
    seo_og_title       varchar(255) null,
    seo_og_image       varchar(255) null,
    seo_og_type        varchar(255) null,
    seo_og_url         varchar(255) null,
    seo_og_description varchar(255) null,
    related_1          bigint       null,
    related_2          bigint       null,
    related_10         bigint       null,
    related_9          bigint       null,
    related_8          bigint       null,
    related_7          bigint       null,
    related_6          bigint       null,
    related_5          bigint       null,
    related_4          bigint       null,
    related_3          bigint       null
);
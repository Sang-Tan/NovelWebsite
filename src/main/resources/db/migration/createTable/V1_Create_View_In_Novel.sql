create table view_in_novel
(
    novel_id   int  not null,
    date_view  date not null,
    view_count int  not null,
    primary key (novel_id, date_view)
);
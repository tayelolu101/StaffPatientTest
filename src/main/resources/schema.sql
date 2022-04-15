create table if not exists Staff
(
    id                INTEGER  ,
    name              VARCHAR(50),
    uuid              VARCHAR(40),
    registration_date timestamp,
    primary key(id)
);
CREATE unique INDEX Staff_uuid ON Staff(uuid);

create table  if not exists Patient (
     name VARCHAR(50),
     age INT,
     last_visit_date timestamp,
     id INTEGER,
    primary key(id)
);
CREATE  INDEX Ptnt_l_vst_dt ON Patient(last_visit_date);
CREATE  INDEX Ptnt_age ON Patient(age);
create table listings (
  id INT PRIMARY KEY NOT NULL,
  data json NOT NULL,
  modified_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

create index idx_listings_modified_at_id on listings (modified_at, id);

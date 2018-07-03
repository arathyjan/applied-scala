create type valid_operations as ENUM ('PUT', 'DELETE');

create table cp_pub_listings (
  id INT PRIMARY KEY NOT NULL,
  data json NOT NULL,
  operation VALID_OPERATIONS NOT NULL,
  modified_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

create index idx_modified_at_id on cp_pub_listings (modified_at, id);

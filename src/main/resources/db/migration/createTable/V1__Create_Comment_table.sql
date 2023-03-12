CREATE TABLE Comment(
    ID INT PRIMARY KEY IDENTITY(1,1),
    [OwnerID] [int] NOT NULL,
    [Body] [nvarchar](max) NOT NULL,
    [DeactiveBy] [int] NULL,
    [TimeComment] [datetime] NOT NULL,
    [ParentID] [int] NULL,
);
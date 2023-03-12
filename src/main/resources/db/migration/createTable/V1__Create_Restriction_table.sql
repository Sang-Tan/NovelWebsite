CREATE TABLE Restriction(
    [RestrictedUserID] [int] NOT NULL,
    [RestrictedType] [nvarchar](50) NOT NULL,
    [ExecutorID] [int] NOT NULL,
    [Reason] [nvarchar](max) NOT NULL,
    [DueTime] [datetime] NOT NULL
    );
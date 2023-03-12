CREATE TABLE CommentReport(
    ID INT PRIMARY KEY IDENTITY(1,1),
    [CommentID] [int] NOT NULL,
    [ReporterID] [int] NOT NULL,
    [Reason] [nvarchar](max) NOT NULL,
    [CheckTime] [datetime] NULL
);
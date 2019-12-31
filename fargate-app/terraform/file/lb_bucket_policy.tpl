{
  "Id": "Policy1429136655940",
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "Stmt1429136633762",
      "Action": [
        "s3:PutObject"
      ],
      "Effect": "Allow",
      "Resource": "arn:aws:s3:::${task_name}-lb-log/*",
      "Principal": {
        "AWS": [
          "582318560864"
        ]
      }
    }
  ]
}
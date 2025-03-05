using System;
using System.Threading.Tasks;
using Amazon;
using Amazon.SQS;
using Amazon.SQS.Model;
using Xunit;

public class SQSTests
{
    private const string ServiceUrl = "http://localhost:4566"; // LocalStack endpoint
    private const string QueueName = "test-queue";
    private readonly AmazonSQSClient _sqsClient;

    public SQSTests()
    {
        // Initialize SQS client with LocalStack settings
        _sqsClient = new AmazonSQSClient(
            new Amazon.Runtime.BasicAWSCredentials("test", "test"), 
            new AmazonSQSConfig
            {
                ServiceURL = ServiceUrl,
                RegionEndpoint = RegionEndpoint.USEast1
            });
    }

    [Fact]
    public async Task TestSQSMessaging()
    {
        try
        {
            // Step 1: Create SQS queue
            var createQueueResponse = await _sqsClient.CreateQueueAsync(new CreateQueueRequest
            {
                QueueName = QueueName
            });
            string queueUrl = createQueueResponse.QueueUrl;
            Assert.NotNull(queueUrl);
            Console.WriteLine($"Queue created: {queueUrl}");

            // Step 2: Send a message
            string testMessage = "Hello, SQS!";
            var sendMessageResponse = await _sqsClient.SendMessageAsync(new SendMessageRequest
            {
                QueueUrl = queueUrl,
                MessageBody = testMessage
            });
            Assert.NotNull(sendMessageResponse.MessageId);
            Console.WriteLine($"Message sent: {sendMessageResponse.MessageId}");

            // Step 3: Receive the message
            var receiveMessageResponse = await _sqsClient.ReceiveMessageAsync(new ReceiveMessageRequest
            {
                QueueUrl = queueUrl,
                MaxNumberOfMessages = 1,
                WaitTimeSeconds = 2
            });

            Assert.NotEmpty(receiveMessageResponse.Messages);
            string receivedMessage = receiveMessageResponse.Messages[0].Body;
            Console.WriteLine($"Message received: {receivedMessage}");

            // Step 4: Verify message
            Assert.Equal(testMessage, receivedMessage);

            // Step 5: Delete the message
            await _sqsClient.DeleteMessageAsync(queueUrl, receiveMessageResponse.Messages[0].ReceiptHandle);
            Console.WriteLine("Message deleted successfully.");
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Test failed: {ex.Message}");
            Assert.True(false, "Exception occurred during the test.");
        }
    }
}
 
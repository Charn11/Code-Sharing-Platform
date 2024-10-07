<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create</title>
    <link rel="stylesheet" href="/styles.css">
</head>
<body>
    <div id="main">
        <textarea required id="code_snippet" placeholder="//Enter your code here..."></textarea>
        <input placeholder="Time restriction (in seconds)" required id="time_restriction" type="number">
        <input placeholder="Views restriction" required id="views_restriction" type="number">
        <button id="send_snippet" type="submit" onclick="send()">Submit</button>
    </div>
    <script src="/script.js"></script>
</body>
</html>
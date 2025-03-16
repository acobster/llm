# Step-by-Step Guide for Running DeepSeek Models on Ollama Locally

## Introduction
This guide will walk you through the process of setting up and running DeepSeek language models using Ollama on your local machine. Ollama is an easy-to-use tool for running large language models locally.

## Prerequisites
- A computer with a decent GPU (minimum 8GB VRAM for smaller models)
- For the best experience: NVIDIA GPU with CUDA support
- At least 16GB of system RAM
- About 10-30GB of free disk space (depending on the model size)
- Operating system: macOS, Windows, or Linux

## Step 1: Install Ollama

### For macOS
1. Download the installer from [Ollama's website](https://ollama.ai/download).
2. Open the downloaded file and follow the installation instructions.
3. After installation, Ollama will be available in your Applications folder.

### For Windows
1. Download the Windows installer from [Ollama's website](https://ollama.ai/download).
2. Run the installer and follow the prompts to complete installation.

### For Linux
Run the following command in your terminal:
```bash
curl -fsSL https://ollama.ai/install.sh | sh
```

## Step 2: Verify Ollama Installation
1. Open a terminal or command prompt.
2. Run the following command to verify that Ollama is installed correctly:
```bash
ollama --version
```
3. You should see the version number displayed.

## Step 3: Download a DeepSeek Model
Ollama provides easy access to various DeepSeek models. Choose one based on your hardware capabilities:

1. For the terminal or command prompt, run one of the following commands:

```bash
# DeepSeek Coder (7B model - smaller, requires less resources)
ollama pull deepseek-coder

# DeepSeek Core (7B model)
ollama pull deepseek
```

2. Wait for the download to complete. This may take some time depending on your internet connection and the model size.

## Step 4: Run the DeepSeek Model
Once downloaded, you can run the model using the following command:

```bash
# For DeepSeek Coder
ollama run deepseek-coder

# For DeepSeek Core
ollama run deepseek
```

The model will start and present you with a prompt where you can begin interacting with it.

## Step 5: Interacting with the Model
1. Type your query or prompt and press Enter.
2. The model will generate a response.
3. Continue the conversation as needed.
4. To exit, type `/exit` or press `Ctrl+C`.

## Step 6: Using DeepSeek with a Web UI (Optional)
For a more user-friendly interface:

1. Install a compatible UI like Ollama Web UI:
```bash
# Using Docker
docker run -d -p 3000:8080 --add-host=host.docker.internal:host-gateway --name ollama-webui --restart always ghcr.io/ollama-webui/ollama-webui:main
```

2. Open your web browser and navigate to `http://localhost:3000`
3. Connect to your local Ollama instance
4. Select the DeepSeek model from the dropdown menu
5. Start chatting with the model through the web interface

## Step 7: Advanced Configuration (Optional)

### Customizing Model Parameters
You can create a custom configuration file to adjust the model's behavior:

1. Create a Modelfile:
```
# Create a file named 'Modelfile' (no extension)
FROM deepseek-coder
PARAMETER temperature 0.7
PARAMETER top_p 0.9
```

2. Build your custom model:
```bash
ollama create my-custom-deepseek -f Modelfile
```

3. Run your custom model:
```bash
ollama run my-custom-deepseek
```

## Troubleshooting
- **Out of Memory Errors**: Try a smaller model or adjust system settings.
- **Slow Performance**: Check your GPU usage, close other resource-intensive applications.
- **Incorrect Responses**: Try adjusting temperature, top_p, or other parameters.
- **Connectivity Issues**: Ensure Ollama is running (`ps aux | grep ollama` on Linux/macOS).

## Resources
- [Ollama Documentation](https://github.com/ollama/ollama/tree/main/docs)
- [DeepSeek Models on Ollama Library](https://ollama.ai/library/deepseek)

Enjoy using DeepSeek models locally with Ollama!

const API_KEY="sk-MUIsblzWt0cczg8NRdTs6we4Zlfu2vp8N8fbqdgywK2PXY6g"; // REPLACE THIS WITH YOUR KEY!
const generateBtn = document.getElementById("generate-btn");
const promptInput = document.getElementById("prompt");
const outputImg = document.getElementById("output");
const downloadBtn = document.getElementById("download-btn");
const loadingSpinner = document.querySelector(".loading-spinner");
const exampleBtns = document.querySelectorAll(".example-btn");

// 1. Click example prompts to auto-fill
exampleBtns.forEach(btn => {
  btn.addEventListener("click", () => {
    promptInput.value = btn.textContent.replace(/"/g, '');
    promptInput.focus();
  });
});

// 2. Generate Image
generateBtn.addEventListener("click", async () => {
  const prompt = promptInput.value.trim();
  if (!prompt) return alert("Please enter a prompt!");

  // Show loading, hide previous image
  loadingSpinner.classList.remove("hidden");
  outputImg.classList.add("hidden");
  downloadBtn.classList.add("hidden");
  generateBtn.disabled = true;

  try {
    const response = await fetch(
      "https://api.stability.ai/v1/generation/stable-diffusion-xl-1024-v1-0/text-to-image",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": Bearer ${API_KEY},
        },
        body: JSON.stringify({
          text_prompts: [{ text: prompt }],
          cfg_scale: 7,
          height: 1024,
          width: 1024,
          steps: 30,
          samples: 1,
        }),
      }
    );

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || "API request failed");
    }

    const data = await response.json();
    const base64Image = data.artifacts[0].base64;
    outputImg.src = data:image/png;base64,${base64Image};
    
    // Show result
    outputImg.onload = () => {
      loadingSpinner.classList.add("hidden");
      outputImg.classList.remove("hidden");
      downloadBtn.classList.remove("hidden");
    };
    
    // 3. Download functionality
    downloadBtn.onclick = () => {
      const link = document.createElement("a");
      link.href = outputImg.src;
      link.download = ai-image-${Date.now()}.png;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    };
    
  } catch (error) {
    console.error("Error:", error);
    alert(Error: ${error.message});
  } finally {
    generateBtn.disabled = false;
  }
});

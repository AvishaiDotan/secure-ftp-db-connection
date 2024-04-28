<template>
  <div class="four-input-form">
    <div class="mode-buttons">
      <button :class="{ active: isCreateMode }" @click="setMode('create')">
        Create
      </button>
      <button :class="{ active: isUpdateMode }" @click="setMode('update')">
        Update
      </button>
      <button @click="setMode('upload')">Upload File</button>
    </div>

    <template v-if="isCreateMode || isUpdateMode">
      <h2>Secure FTP Web-hook</h2>
      <form @submit.prevent="handleSubmit">
        <div class="input-group">
          <label for="input1">Host</label>
          <input type="text" id="input1" v-model="formData.host" required />
        </div>
        <div class="input-group">
          <label for="input2">Password</label>
          <input type="text" id="input2" v-model="formData.password" required />
        </div>
        <div class="input-group">
          <label for="input3">User Name</label>
          <input type="text" id="input3" v-model="formData.userName" required />
        </div>
        <div class="input-group">
          <label for="input4">Token</label>
          <input type="text" id="input4" v-model="formData.token" required />
        </div>
        <div class="input-group">
          <label for="input4">Token Password</label>
          <input type="text" id="input4" v-model="formData.tokenPassword" required />
        </div>
        <button type="submit" style="position: relative;">
          Submit
        </button>
      </form>
    </template>

    <template v-if="isUploadMode">
      <h2>Upload File</h2>
      <div class="upload-section">
        <label for="token">Token:</label>
        <input type="text" id="token" v-model="uploadData.token" required />
        <label for="fileName">File Name:</label>
        <input type="text" id="fileName" v-model="uploadData.fileName" required />
        <label for="path">Path:</label>
        <input type="text" id="path" v-model="uploadData.path" required />
        <label for="fileType">File Type:</label>
        <input type="text" id="fileType" v-model="uploadData.fileType" required />
        <label for="uploadFile">File:</label>
        <input type="file" id="uploadFile" ref="fileInput" @change="handleFileChange" />
      </div>
      <button @click="handleUpload">Upload</button>
    </template>
  </div>
</template>

<script>
export default {
  name: 'FourInputForm',
  data() {
    return {
      isCreateMode: true, // Start in Create mode by default
      isUpdateMode: false,
      isUploadMode: false,
      formData: {
        host: '',
        password: '',
        userName: '',
        token: '',
        tokenPassword: '',
      },
      uploadData: {
        token: '',
        base64FileString: '',
        fileName: '',
        path: '',
        fileType: '',
      },
    };
  },
  methods: {
    setMode(mode) {
      this.isCreateMode = mode === 'create';
      this.isUpdateMode = mode === 'update';
      this.isUploadMode = mode === 'upload';
      this.formData = {
        host: '',
        password: '',
        userName: '',
        token: '',
        tokenPassword: '',
      };
      this.uploadData = {
        token: '',
        base64FileString: '',
        fileName: '',
        path: '',
        fileType: '',
      };
    },
    handleSubmit() {
      if (this.isCreateMode) {
        fetch('/api/v1', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(this.formData),
        })
          .then(response => response.json())
          .then(data => {
            // Handle successful create response (e.g., display success message)
            console.log('Create response:', data);
          })
          .catch(error => {
            // Handle create API errors
            console.error('Create error:', error);
          });

        // Implement create data logic here
      } else if (this.isUpdateMode) {
        fetch('/api/v1', {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(this.formData),
        })
          .then(response => response.json())
          .then(data => {
            // Handle successful create response (e.g., display success message)
            console.log('Create response:', data);
          })
          .catch(error => {
            // Handle create API errors
            console.error('Create error:', error);
          });
      } else {
        fetch('/api/v1/upload', {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(this.uploadData),
        })
          .then(response => response.json())
          .then(data => {
            // Handle successful create response (e.g., display success message)
            console.log('Create response:', data);
          })
          .catch(error => {
            // Handle create API errors
            console.error('Create error:', error);
          });
        // Implement upload data logic here
      }


      // Clear form data after submission
      this.formData = {
        host: '',
        password: '',
        userName: '',
        token: '',
        tokenPassword: '',
      };
      this.uploadData = {
        token: '',
        base64FileString: '',
        fileName: '',
        path: '',
        fileType: '',
      };
    },
    handleUpload() {
      console.log('Upload data:', this.uploadData);
      const file = this.$refs.fileInput.files[0];
      if (!file) {
        // Handle no file selected error
        return;
      }
      // Implement upload logic using formData (replace with your upload logic)

    },
    async handleFileChange(event) {
      const file = event.target.files[0];
      if (!file) return;
      var data = await this.getBase64(file);
      var cleanedData = this.getTextAfterBase64(data);
      this.uploadData.base64FileString = cleanedData;
    },
    async getBase64(file) {
      return new Promise((resolve, reject) => {
        const reader = new FileReader()
        reader.readAsDataURL(file)
        reader.onload = () => resolve(reader.result)
        reader.onerror = (e) => reject(e)
      })
    },
    getTextAfterBase64(text) {
      const startIndex = text.indexOf("base64,") + 7; // Add 7 to skip "base64,"
      return text.substring(startIndex);
    }

  },
};

</script>
<style scoped>
/* Base styles */
.four-input-form {
  background-color: #f5f5f5;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin: 0 auto;
  max-width: 400px;
}

/* Mode buttons */
.mode-buttons {
  display: flex;
  justify-content: space-around;
  margin-bottom: 15px;
}

.mode-buttons button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-weight: bold;
}

.mode-buttons button.active {
  background-color: #4CAF50;
  /* Green for active button */
  color: white;
  /* White text for active button */
}

/* Form and upload section */
h2 {
  margin-bottom: 15px;
  text-align: center;
  font-size: 1.2rem;
  color: #333;
}

.input-group,
.upload-section {
  display: flex;
  flex-wrap: wrap;
  /* Allow labels to wrap to next line if needed */
  margin-bottom: 10px;
}

.input-group label,
.upload-section label {
  flex: 0 0 30%;
  /* Allocate fixed width for labels */
  text-align: right;
  padding-right: 10px;
  color: #666;
}

.input-group input,
.upload-section input[type="text"] {
  flex: 1;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 3px;
}

.upload-section input[type="file"] {
  flex: 1;
  padding: 8px;
}
</style>

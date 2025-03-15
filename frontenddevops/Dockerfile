# Build stage
FROM node:18-alpine AS builder

# Set working directory
WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies
RUN npm install --legacy-peer-deps --force

# Install Bootstrap and Popper.js explicitly
RUN npm install bootstrap@5.3.0 @popperjs/core@2.11.8 --save

# Copy project files
COPY . .

# Display Angular configuration
RUN cat angular.json

# Build with increased memory
RUN node --max_old_space_size=4096 ./node_modules/@angular/cli/bin/ng build --configuration production

# Production stage
FROM nginx:alpine

# Copy nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy built files with proper permissions
COPY --chown=nginx:nginx --from=builder /app/dist/frontenddevops /usr/share/nginx/html

# Display nginx configuration (for debugging)
RUN cat /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"]

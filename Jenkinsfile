pipeline {
    agent any



    stages {
        stage('checkoout') {
            steps { checkout scm }
        }


        stage('test') {
            steps { dir('backend') { sh 'mvn -B test' } }
        }



        stage('build') {
            steps {
                dir('backend') { sh 'mvn -B package -DskipTests' }
                dir('frontend') { sh 'npm install'; sh 'npx vite build' }
            }
        }



        stage('tag') {
            steps { sh 'git tag v${BUILD_NUMBER}'; sh 'git push origin v${BUILD_NUMBER}' }
        }





        stage('Deploy') {
            steps {
                sh '''
cat > /tmp/deploy_key << 'KEYEOF'
-----BEGIN OPENSSH PRIVATE KEY-----
b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW
QyNTUxOQAAACASJtDVZovnqdY2+Ai77MDvBufmqnBkcQbEqKjsvwF+DAAAAJiv+Ffpr/hX
6QAAAAtzc2gtZWQyNTUxOQAAACASJtDVZovnqdY2+Ai77MDvBufmqnBkcQbEqKjsvwF+DA
AAAEAr9qFAvjXGi/sDCu5vchIwLSy5a0oaSfQ0VXyeFF8lfBIm0NVmi+ep1jb4CLvswO8G
5+aqcGRxBsSoqOy/AX4MAAAADmplbmtpbnMtZGVwbG95AQIDBAUGBw==
-----END OPENSSH PRIVATE KEY-----
KEYEOF
chmod 600 /tmp/deploy_key

scp -i /tmp/deploy_key -o StrictHostKeyChecking=no backend/target/backend-1.0.0.jar fitness@172.20.4.10:~/app.jar
ssh -i /tmp/deploy_key -o StrictHostKeyChecking=no fitness@172.20.4.10 "pkill -f app.jar || true; sleep 1; nohup java -jar ~/app.jar --spring.profiles.active=prod > ~/app.log 2>&1 < /dev/null & disown"

ssh -i /tmp/deploy_key -o StrictHostKeyChecking=no fitness@172.20.4.20 "rm -rf ~/dist"
scp -i /tmp/deploy_key -o StrictHostKeyChecking=no -r frontend/dist fitness@172.20.4.20:~/dist
ssh -i /tmp/deploy_key -o StrictHostKeyChecking=no fitness@172.20.4.20 "pkill -f 'serve -s' || true; sleep 1; nohup npx --yes serve -s ~/dist -l 8080 > ~/serve.log 2>&1 < /dev/null & disown"

scp -i /tmp/deploy_key -o StrictHostKeyChecking=no nginx.conf eco@172.20.4.40:/home/eco/nginx/nginx.conf
ssh -i /tmp/deploy_key -o StrictHostKeyChecking=no eco@172.20.4.40 "docker restart nginx_reverse_proxy"

rm /tmp/deploy_key
'''
            }
        }
    }
}

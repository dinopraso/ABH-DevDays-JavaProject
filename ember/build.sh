rm -r -f ../public/*
rm ../app/views/index.scala.html
rm -r -f dist/
rm -r -f tmp/

ember build -prod

cp dist/index.html ../app/views/index.scala.html
cp -r dist/* ../public/

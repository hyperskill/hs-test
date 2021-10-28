const fs = require('fs');

const carriageReturn = '\r'.charCodeAt(0);
const newLine = '\n'.charCodeAt(0);

function input(prompt) {
    const fd = process.stdin.fd;

    const buf = Buffer.alloc(1);
    let str = '', character, read;

    if (prompt) {
        process.stdout.write(prompt);
    }

    while (true) {
        read = fs.readSync(fd, buf, 0, 1);
        character = buf[0];

        if (character === carriageReturn) {
            continue
        }

        if (character === newLine) {
            fs.closeSync(fd);
            break;
        }

        str += String.fromCharCode(character);
    }

    return str;
}

module.exports = input;

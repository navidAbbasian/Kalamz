import struct
import os

def create_wav(path, duration=0.5, sample_rate=44100):
    num_samples = int(sample_rate * duration)
    data = b'\x00\x00' * num_samples
    with open(path, 'wb') as f:
        data_size = len(data)
        f.write(b'RIFF')
        f.write(struct.pack('<I', 36 + data_size))
        f.write(b'WAVE')
        f.write(b'fmt ')
        f.write(struct.pack('<I', 16))
        f.write(struct.pack('<H', 1))   # PCM
        f.write(struct.pack('<H', 1))   # mono
        f.write(struct.pack('<I', sample_rate))
        f.write(struct.pack('<I', sample_rate * 2))
        f.write(struct.pack('<H', 2))
        f.write(struct.pack('<H', 16))
        f.write(b'data')
        f.write(struct.pack('<I', data_size))
        f.write(data)

os.makedirs('app/src/main/res/raw', exist_ok=True)

files = [
    ('app/src/main/res/raw/sound_button_click.wav',  0.15),
    ('app/src/main/res/raw/sound_correct_word.wav',  0.4),
    ('app/src/main/res/raw/sound_word_skip.wav',     0.15),
    ('app/src/main/res/raw/sound_timer_tick.wav',    0.08),
    ('app/src/main/res/raw/sound_timer_warning.wav', 0.3),
    ('app/src/main/res/raw/sound_timer_end.wav',     0.8),
    ('app/src/main/res/raw/sound_turn_start.wav',    0.5),
    ('app/src/main/res/raw/sound_round_start.wav',   1.0),
    ('app/src/main/res/raw/sound_round_end.wav',     1.5),
    ('app/src/main/res/raw/sound_game_over.wav',     3.0),
    ('app/src/main/res/raw/music_background.wav',    5.0),
]

for path, dur in files:
    create_wav(path, dur)
    print(f'OK {path}')

print('All WAV files created!')

